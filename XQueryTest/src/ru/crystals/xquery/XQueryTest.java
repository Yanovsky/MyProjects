package ru.crystals.xquery;

import ru.crystals.cm.utils.ConfigHelper;

public class XQueryTest {

    public XQueryTest() {

    }

    public static void main(String[] args) throws Exception {
        String homePath = "D:/SVNAll/SetRetail10_JBOSS7/SetRetail10_SCO/CashLoaderSCO";
        String script = "D:/Public/Sprint 45 JBOSS7/sco_10.2.1.4_10.2.1.6/restore/xml_U_10.2.1.6_002.xq";
        ConfigHelper.runScriptXQ(homePath, script);
    }
}
/*
 * <!--property key="connectorClass" value="ru.crystals.sco.itab.ITABController" /-->
 */

/*
 * <property key="connectorClass" value="ru.crystals.sco.itab.SCOConnector" />
 */