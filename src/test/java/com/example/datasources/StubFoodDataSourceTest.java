package com.example.datasources;

import com.example.model.Cuisine;
import com.example.model.Dessert;

import com.example.model.MainCourse;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubFoodDataSourceTest {
    private StubFoodDataSource stubFoodDataSource;

    private MainCourse mainCourse1;
    private MainCourse mainCourse2;
    private MainCourse mainCourse3;

    private Dessert dessert1;
    private Dessert dessert2;
    private Dessert dessert3;


    @Before
    public void setUp() throws Exception {
        mainCourse1 = mock(MainCourse.class);
        mainCourse2 = mock(MainCourse.class);
        mainCourse3 = mock(MainCourse.class);

        dessert1 = mock(Dessert.class);
        dessert2 = mock(Dessert.class);
        dessert3 = mock(Dessert.class);


    }


    @Test
    public void shouldReturnOnlyPolishMainCourses() throws Exception {
        //given
        when(mainCourse1.getCuisine()).thenReturn(Cuisine.MEXICAN);
        when(mainCourse2.getCuisine()).thenReturn(Cuisine.ITALIAN);
        when(mainCourse3.getCuisine()).thenReturn(Cuisine.POLISH);
        stubFoodDataSource = new StubFoodDataSource(mock(List.class), Arrays.asList(mainCourse1, mainCourse2, mainCourse3), mock(List.class));

        //when
        List<MainCourse> mainCourses = stubFoodDataSource.getListOfMainCourses(Cuisine.POLISH);

        //then
        assertThat(mainCourses).isEqualTo(Arrays.asList(mainCourse3));
    }

    @Test
    public void shouldReturnEmptyListOfMainCourses() throws Exception {
        //given
        when(mainCourse1.getCuisine()).thenReturn(Cuisine.MEXICAN);
        when(mainCourse2.getCuisine()).thenReturn(Cuisine.POLISH);
        when(mainCourse3.getCuisine()).thenReturn(Cuisine.MEXICAN);
        stubFoodDataSource = new StubFoodDataSource(mock(List.class), Arrays.asList(mainCourse1, mainCourse2, mainCourse3), mock(List.class));

        //when
        List<MainCourse> mainCourses = stubFoodDataSource.getListOfMainCourses(Cuisine.ITALIAN);

        //then
        assertThat(mainCourses).isEqualTo(Arrays.asList());
    }

    @Test
    public void shouldReturnOnlyPolishDesserts() throws Exception {
        //given
        when(dessert1.getCuisine()).thenReturn(Cuisine.MEXICAN);
        when(dessert2.getCuisine()).thenReturn(Cuisine.POLISH);
        when(dessert3.getCuisine()).thenReturn(Cuisine.MEXICAN);
        stubFoodDataSource = new StubFoodDataSource(mock(List.class), mock(List.class), Arrays.asList(dessert1, dessert2, dessert3));

        //when
        List<Dessert> mainCourses = stubFoodDataSource.getListOfDesserts(Cuisine.POLISH);

        //then
        assertThat(mainCourses).isEqualTo(Arrays.asList(dessert2));
    }

    @Test
    public void shouldReturnEmptyListOfDesserts() throws Exception {
        //given
        when(dessert1.getCuisine()).thenReturn(Cuisine.MEXICAN);
        when(dessert2.getCuisine()).thenReturn(Cuisine.POLISH);
        when(dessert3.getCuisine()).thenReturn(Cuisine.MEXICAN);
        stubFoodDataSource = new StubFoodDataSource(mock(List.class), mock(List.class), Arrays.asList(dessert1, dessert2, dessert3));

        //when
        List<Dessert> mainCourses = stubFoodDataSource.getListOfDesserts(Cuisine.ITALIAN);

        //then
        assertThat(mainCourses).isEqualTo(Arrays.asList());
    }

}