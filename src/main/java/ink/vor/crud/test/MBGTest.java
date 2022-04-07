package ink.vor.crud.test;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muquanrui
 * @date 14/02/2022 14:41
 */
public class MBGTest {
    @Test
    public void testMBG() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException, URISyntaxException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File(new MBGTest().getClass().getClassLoader().getResource("mbg.xml").toURI());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
