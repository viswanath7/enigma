package nl.ing.bank.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;


public class RightRotorTest {

  private RightRotor rightRotor;

  @Before
  public void setUp() throws Exception {
    rightRotor = new RightRotor('K');
  }

  @Test
  public void should_get_initialised_window_properly() {
    final Map<Long, Pair<Character, Character>> initialisedWindow = rightRotor.getInitialisedWindow();
    assertThat(initialisedWindow, is(notNullValue()));
    assertThat(initialisedWindow.size(), equalTo(26));
    assertThat(initialisedWindow.get(25L).getLeft(), equalTo('J'));
  }

  @Test
  public void should_correctly_show_window_when_moved_up_by_one_row() throws Exception {
    final Map<Long, Pair<Character, Character>> initialisedWindow = rightRotor.getInitialisedWindow();
    final Map<Long, Pair<Character, Character>> newWindow = rightRotor.upByOneRow(initialisedWindow);
    assertThat(newWindow, is(notNullValue()));
    assertThat(newWindow.size(), equalTo(26));
    assertThat(newWindow.get(25L).getLeft(), equalTo('K'));
  }

}