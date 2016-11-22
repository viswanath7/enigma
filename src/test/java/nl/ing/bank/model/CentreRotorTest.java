package nl.ing.bank.model;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class CentreRotorTest {

  private CentreRotor centreRotor;

  @Before
  public void setUp() throws Exception {
    centreRotor = new CentreRotor('C');
  }

  @Test
  public void should_initialise_window_properly() throws Exception {
    final Map<Long, Pair<Character, Character>> initialisedWindow = centreRotor.getInitialisedWindow();
    assertThat(initialisedWindow, is(notNullValue()));
    assertThat(initialisedWindow.size(), equalTo(26));
    assertThat(initialisedWindow.get(25L).getLeft(), equalTo('B'));
  }
}