package suites;

import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("ie.atu.sw")
@IncludePackages({
        "ie.atu.sw.account",
        "ie.atu.sw.manager",
        "ie.atu.sw.transaction",
        "ie.atu.sw.validation"
})
public class AllTests {
}
