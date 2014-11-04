package suite;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import category.MyPageFailedTestCategory;

@RunWith(Categories.class)
@IncludeCategory(MyPageFailedTestCategory.class)
@SuiteClasses({ AllTests.class })
public class FailedCategoryTest {

}
