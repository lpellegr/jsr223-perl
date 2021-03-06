/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package jsr223.perl;

import java.util.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import jsr223.perl.utils.PerlVersionGetter;
import processbuilder.PerlSingletonPerlProcessBuilderFactory;
import processbuilder.utils.PerlProcessBuilderUtilities;


public class PerlScriptEngineFactory implements ScriptEngineFactory {
    private final Map<String, Object> parameters = new HashMap<>();

    private static PerlProcessBuilderUtilities processBuilderUtilities = new PerlProcessBuilderUtilities();

    private static PerlVersionGetter perlVersionGetter = new PerlVersionGetter(processBuilderUtilities);

    // Script engine parameters
    private static final String NAME = "perl";

    private static final String ENGINE = NAME;

    private static final String PERL_VERSION = perlVersionGetter.getPerlVersion(PerlSingletonPerlProcessBuilderFactory.getInstance());

    private static final String ENGINE_VERSION = (PERL_VERSION.equals("") ? "0.1.0" : PERL_VERSION);

    private static final String LANGUAGE = "perl";

    public PerlScriptEngineFactory() {
        parameters.put(ScriptEngine.NAME, NAME);
        parameters.put(ScriptEngine.ENGINE_VERSION, ENGINE_VERSION);
        parameters.put(ScriptEngine.LANGUAGE, LANGUAGE);
        parameters.put(ScriptEngine.ENGINE, ENGINE);
    }

    public PerlScriptEngineFactory(PerlProcessBuilderUtilities processBuilderUtilities,
            PerlVersionGetter perlVersionGetter) {
        this();
        if (processBuilderUtilities == null || perlVersionGetter == null) {
            throw new NullPointerException("processBuilderUtilities and perlVersionGetter must not be null");
        }
        this.processBuilderUtilities = processBuilderUtilities;
        this.perlVersionGetter = perlVersionGetter;

    }

    @Override
    public String getEngineName() {
        return (String) parameters.get(ScriptEngine.NAME);
    }

    @Override
    public String getEngineVersion() {
        return (String) parameters.get(ScriptEngine.ENGINE_VERSION);
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("pl", "pm", "t", "pod");
    }

    @Override
    public List<String> getMimeTypes() {
        return Collections.singletonList("application/x-perl");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList(ENGINE, "perl", "Perl");
    }

    @Override
    public String getLanguageName() {
        return (String) parameters.get(ScriptEngine.LANGUAGE);
    }

    @Override
    public String getLanguageVersion() {
        return perlVersionGetter.getPerlVersion(PerlSingletonPerlProcessBuilderFactory.getInstance());
    }

    @Override
    public Object getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        String methodCall = m + " ";
        for (String arg : args) {
            methodCall += arg + " ";
        }
        return methodCall;
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return toDisplay;
    }

    @Override
    public String getProgram(String... statements) {
        return statements[0];
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new PerlScriptEngine();
    }
}
