/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.languages;

import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.SupportingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhpSlimServerCodegen extends AbstractPhpCodegen {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhpSlimServerCodegen.class);

    protected String groupId = "org.openapitools";
    protected String artifactId = "openapi-server";

    public PhpSlimServerCodegen() {
        super();

        // clear import mapping (from default generator) as slim does not use it
        // at the moment
        importMapping.clear();

        variableNamingConvention = "camelCase";
        artifactVersion = "1.0.0";
        packagePath = ""; // empty packagePath (top folder)
        invokerPackage = camelize("OpenAPIServer");
        modelPackage = packagePath + "\\Models";
        apiPackage = packagePath;
        outputFolder = "generated-code" + File.separator + "slim";

        // no api files
        apiTemplateFiles.clear();
        // no test files
        apiTestTemplateFiles.clear();
        // no doc files
        modelDocTemplateFiles.clear();
        apiDocTemplateFiles.clear();

        embeddedTemplateDir = templateDir = "php-slim-server";

        // additionalProperties.put(CodegenConstants.INVOKER_PACKAGE, invokerPackage);
        additionalProperties.put(CodegenConstants.GROUP_ID, groupId);
        additionalProperties.put(CodegenConstants.ARTIFACT_ID, artifactId);
        // additionalProperties.put(CodegenConstants.ARTIFACT_VERSION, artifactVersion);
        
        // override cliOptions from AbstractPhpCodegen
        for (CliOption co : cliOptions) {
            if (co.getOpt().equals(AbstractPhpCodegen.VARIABLE_NAMING_CONVENTION)) {
                co.setDescription("naming convention of variable name, e.g. camelCase.");
                co.setDefault("camelCase");
                break;
            }
        }

        supportingFiles.add(new SupportingFile("README.mustache", packagePath.replace('/', File.separatorChar), "README.md"));
        supportingFiles.add(new SupportingFile("composer.json", packagePath.replace('/', File.separatorChar), "composer.json"));
        supportingFiles.add(new SupportingFile("index.mustache", packagePath.replace('/', File.separatorChar), "index.php"));
        supportingFiles.add(new SupportingFile(".htaccess", packagePath.replace('/', File.separatorChar), ".htaccess"));
        supportingFiles.add(new SupportingFile(".gitignore", packagePath.replace('/', File.separatorChar), ".gitignore"));
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
        return "php-slim";
    }

    @Override
    public String getHelp() {
        return "Generates a PHP Slim Framework server library.";
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        List<CodegenOperation> operationList = (List<CodegenOperation>) operations.get("operation");
        for (CodegenOperation op : operationList) {
            if (op.hasProduces) {
                // need to escape */* values because they breakes current mustaches
                List<Map<String, String>> c = op.produces;
                for (Map<String, String> mediaType : c) {
                    if ("*/*".equals(mediaType.get("mediaType"))) {
                        mediaType.put("mediaType", "*_/_*");
                    }
                }
            }
        }
        return objs;
    }

    @Override
    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        Map<String, Object> apiInfo = (Map<String, Object>) objs.get("apiInfo");
        List<HashMap<String, Object>> apiList = (List<HashMap<String, Object>>) apiInfo.get("apis");
        for (HashMap<String, Object> api : apiList) {
            HashMap<String, Object> operations = (HashMap<String, Object>) api.get("operations");
            List<CodegenOperation> operationList = (List<CodegenOperation>) operations.get("operation");

            // Sort operations to avoid static routes shadowing
            // ref: https://github.com/nikic/FastRoute/blob/master/src/DataGenerator/RegexBasedAbstract.php#L92-L101
            Collections.sort(operationList, new Comparator<CodegenOperation>() {
                @Override
                public int compare(CodegenOperation one, CodegenOperation another) {
                    if (one.getHasPathParams() && !another.getHasPathParams()) return 1;
                    if (!one.getHasPathParams() && another.getHasPathParams()) return -1;
                    return 0;
                }
            });
        }
        return objs;
    }

}
