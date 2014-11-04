package suite;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import category.MyPageSuccessTestCategory;

@RunWith(Categories.class)
@SuiteClasses({ AllTests.class })
@IncludeCategory( MyPageSuccessTestCategory.class )
public class SuccessCategoryTest {

}
