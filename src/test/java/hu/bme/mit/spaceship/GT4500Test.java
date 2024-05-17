package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore testObject1 = mock(TorpedoStore.class);
  private TorpedoStore testObject2 = mock(TorpedoStore.class);

  @BeforeEach
  public void init(){
    this.ship = new GT4500(testObject1, testObject2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when (testObject1.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(testObject1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when (testObject1.fire(1)).thenReturn(true);
    when (testObject2.fire(1)).thenReturn(true);
    when (testObject1.isEmpty()).thenReturn(false);
    when (testObject2.isEmpty()).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(testObject1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_OneFired(){
    when (testObject1.getTorpedoCount()).thenReturn(1);
    when (testObject2.getTorpedoCount()).thenReturn(1);
    when (testObject1.isEmpty()).thenReturn(false);
    when (testObject2.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(testObject1, times(1)).fire(1);
    verify(testObject2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Thrice_Primary_Having_One_Bullet(){
    when (testObject1.getTorpedoCount()).thenReturn(1);
    when (testObject2.getTorpedoCount()).thenReturn(2);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    when (testObject1.isEmpty()).thenReturn(true);

    result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(testObject1, times(1)).fire(1);
    verify(testObject2, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Switching_Store(){
    when (testObject1.getTorpedoCount()).thenReturn(2);
    when (testObject2.getTorpedoCount()).thenReturn(1);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(testObject1, times(1)).fire(1);
    verify(testObject2, times(1)).fire(1);
  }

}
