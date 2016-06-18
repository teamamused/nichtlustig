package teamamused.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import teamamused.common.models.GameBoardTest;
import teamamused.common.models.PlayerTest;

/**
 * teamamused.common Testsuite um alle Tests des common Projektes auf einmal auszuführen.
 * 
 * @author Daniel
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	GameBoardTest.class,
	PlayerTest.class
})
public class CommonTestSuite {

}
