package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.CSVWriterTest;
import test.MyPageClientTest;

@RunWith(Suite.class)
@SuiteClasses({ CSVWriterTest.class, MyPageClientTest.class })
public class AllTests {

}
