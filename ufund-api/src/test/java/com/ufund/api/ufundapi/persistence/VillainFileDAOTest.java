package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Scheme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Villain File DAO class
 * 
 * @author Isaac Soares, Nadeem Mustafa, Jacky Chan
 */


@Tag("Persistence-tier")
public class VillainFileDAOTest {
    VillainFileDAO VillainFileDAO;
    Scheme[] testSchemes;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupVillainFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testSchemes = new Scheme[3];
        testSchemes[0] = new Scheme(99,"Dr. Freeze", "Freeze America", 36000);
        testSchemes[1] = new Scheme(100,"Agental00", "Freeze 2K20", 34000);
        testSchemes[2] = new Scheme(101,"TSiftal", "waste all carbon emissions", 32000);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the villain array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Scheme[].class))
                .thenReturn(testSchemes);
        VillainFileDAO = new VillainFileDAO("doesnt_matter.txt",mockObjectMapper);

    }

    /**
    * Test method to verify the behavior of {@code getSchemes()} method in {@code VillainFileDAO}.
    * It invokes the method and compares the returned schemes with the test schemes.
    */
    @Test
    public void testGetSchemes() {
        // Invoke
        Scheme[] schemes = VillainFileDAO.getSchemes();

        // Analyze
        assertEquals(schemes.length,testSchemes.length);
        for (int i = 0; i < testSchemes.length;++i)
            assertEquals(schemes[i],testSchemes[i]);
    }

    /**
    * Test method to verify the behavior of {@code findSchemes(String)} method in {@code VillainFileDAO}.
    * It invokes the method with a search term and verifies the returned schemes.
    */
    @Test
    public void testFindSchemes() {   //may have to make more for different finds in each text.
        // Invoke
        Scheme[] schemes = VillainFileDAO.findSchemes("al");

        // Analyze
        assertEquals(schemes.length,2);
        assertEquals(schemes[0],testSchemes[1]);
        assertEquals(schemes[1],testSchemes[2]);
    }

    /**
    * Test method to verify the behavior of {@code getScheme(int)} method in {@code VillainFileDAO}.
    * It invokes the method with a scheme ID and checks the returned scheme.
    */
    @Test
    public void testGetScheme() {
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme(99);

        // Analzye
        assertEquals(scheme,testSchemes[0]);
    }

    /**
    * Test method to verify the behavior of {@code deleteScheme(int)} method in {@code VillainFileDAO}.
    * It invokes the method to delete a scheme and checks the result and internal state.
    */
    @Test
    public void testDeleteScheme() {  //changed schemes to public to make this work, see if this does anything.
        // Invoke
        boolean result = assertDoesNotThrow(() -> VillainFileDAO.deleteScheme(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test villain array - 1 (because of the delete)
        // Because villain attribute of VillainFileDAO is package private
        // we can access it directly
        assertEquals(VillainFileDAO.schemes.size(),testSchemes.length-1);
    }

    /**
    * Test method to verify the behavior of {@code createScheme(Scheme)} method in {@code VillainFileDAO}.
    * It creates a new scheme, invokes the method, and checks the result and the scheme's presence.
    */
    @Test
    public void testCreateScheme() {
        // Setup
        Scheme scheme = new Scheme(102, "clayman", "world to clay", 30000);

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.createScheme(scheme),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        // Verify the existence of the created scheme by name
        Scheme actual = VillainFileDAO.getScheme_str(scheme.getName());
        assertNotNull(actual);
        assertEquals(scheme.getId(), actual.getId());
        assertEquals(scheme.getName(), actual.getName());
        assertEquals(scheme.getTitle(), actual.getTitle());
    }

    /**
     * Tests creating a scheme with an existing ID and name in {@link VillainFileDAO}, expecting no duplication.
     * 
     * @throws IOException
     */
    @Test
    public void testCreateSchemeWithExistingIdAndName() throws IOException {
        // Assuming that the ID 99 and name "Dr. Freeze" already exist
        Scheme existingScheme = new Scheme(99, "Dr. Freeze", "Freeze America", 28000);

        // Invoke
        Scheme result = VillainFileDAO.createScheme(existingScheme);

        // Analyze
        assertNull(result, "Expected null as the scheme with the same ID and name already exists");
    }

    /**
    * Test method to verify the behavior of {@code updateScheme(Scheme)} method in {@code VillainFileDAO}.
    * It updates an existing scheme, invokes the method, and checks the result and the updated scheme.
    */
    @Test
    public void testUpdateScheme() {
        // Setup
        Scheme scheme = new Scheme(99,"Omni-Man", "Conquer Earth", 26000);

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.updateScheme(scheme),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Scheme actual = VillainFileDAO.getScheme(scheme.getId());
        assertEquals(actual,scheme);
    }

    /**
    * Test method to verify the exception handling in {@code createScheme(Scheme)} method of {@code VillainFileDAO}.
    * It simulates an IOException during saving and verifies that the exception is properly thrown.
    * @throws IOException if an error occurs during the test
    */
    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Scheme[].class));

        Scheme scheme = new Scheme(102,"Wi-Fire", "spread fire", 24000);

        assertThrows(IOException.class,
                        () -> VillainFileDAO.createScheme(scheme),
                        "IOException not thrown");
    }
    
    /**
     * Test method to verify the behavior when a scheme is not found in {@code VillainFileDAO}.
     * It invokes the method to retrieve a scheme with a non-existing ID and checks the result.
     */
    @Test
    public void testGetSchemeNotFound() {
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme(98);

        // Analyze
        assertEquals(scheme,null);
    }

    /**
     * Test method to verify the behavior when deleting a non-existing scheme in {@code VillainFileDAO}.
     * It invokes the method to delete a scheme with a non-existing ID and checks the result and internal state.
     */
    @Test
    public void testDeleteSchemeNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> VillainFileDAO.deleteScheme(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(VillainFileDAO.schemes.size(),testSchemes.length);
    }

    /**
     * Test method to verify the behavior when updating a non-existing scheme in {@code VillainFileDAO}.
     * It invokes the method to update a scheme with a non-existing ID and checks the result.
     */
    @Test
    public void testUpdateSchemeNotFound() {
        // Setup
        Scheme scheme = new Scheme(98,"Bolt", "Destroy cats", 22000);

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.updateScheme(scheme), "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    /**
     * Test method to verify the exception handling in the constructor of {@code VillainFileDAO}.
     * It simulates an IOException during initialization and verifies that the exception is properly thrown.
     * @throws IOException if an error occurs during the test
     */
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the VillainFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Scheme[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new VillainFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    /**
     * Tests the retrieval of schemes by their title.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByTitle() throws IOException {
        // Setup
        String searchTitle = "Freeze";
        Scheme[] expectedSchemes = {testSchemes[0], testSchemes[1]}; // Schemes with titles containing "Freeze"
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByTitle(searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
        for (int i = 0; i < expectedSchemes.length; i++) {
            assertEquals(expectedSchemes[i], foundSchemes[i]); // Assert each element of the arrays
        }
    }

    /**
     * Tests the behavior of finding schemes by title with a {@code null} title.
     * 
     * Expect to find no schemes as the title is null.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByTitle_NullTitle() throws IOException {
        // Setup
        String searchTitle = null;
        Scheme[] expectedSchemes = {}; // No schemes with a null title
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByTitle(searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
        for (int i = 0; i < expectedSchemes.length; i++) {
            assertEquals(expectedSchemes[i], foundSchemes[i]); // Assert each element of the arrays
        }
    }

    /**
     * Tests finding schemes by title where a scheme with a {@code null} title exists.
     * 
     * Ensures that schemes with null titles are not included in the results.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByTitle_WithTitleNull() throws IOException {
        // Setup: Assuming one of the schemes has a null title, either add this to setup or mock it
        Scheme schemeWithNullTitle = new Scheme(103, "NullTitleVillain", null, 25000);
        VillainFileDAO.schemes.put(schemeWithNullTitle.getId(), schemeWithNullTitle);
    
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByTitle("NonExistingTitle");
    
        // Analyze
        // Ensure the scheme with a null title is not included in the results
        for (Scheme scheme : foundSchemes) {
            assertNotNull(scheme.getTitle(), "Scheme with null title should not be included in the results.");
        }
    }    

    /**
     * Tests finding schemes by exact name match.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByName() throws IOException {
        // Setup
        String searchName = "Dr. Freeze";
        Scheme[] expectedSchemes = {testSchemes[0]}; // Schemes with names exactly "Dr. Freeze"
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByName(searchName);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
        for (int i = 0; i < expectedSchemes.length; i++) {
            assertEquals(expectedSchemes[i], foundSchemes[i]); // Assert each element of the arrays
        }
    }

    /**
     * Tests the behavior of finding schemes by name with a {@code null} name.
     * <br>
     * Expect to find no schemes as the name is null.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByName_NullName() throws IOException {
        // Setup
        String searchName = null;
        Scheme[] expectedSchemes = new Scheme[0]; // No schemes with a null name
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByName(searchName);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
        for (int i = 0; i < expectedSchemes.length; i++) {
            assertEquals(expectedSchemes[i], foundSchemes[i]); // Assert each element of the arrays
        }
    }

    /**
     * Tests the retrieval of schemes by a name that does not exist.
     * <br>
     * Verifies that no schemes are returned for a non-existing name.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByName_NotFound() throws IOException {
        // Setup: a name that does not exist in any Scheme
        String searchName = "NonExistingName";
        Scheme[] expectedSchemes = {}; // No schemes with this name exist

        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByName(searchName);

        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes, "Expected an empty array when no schemes with the given name are found");
    }

    /**
     * Tests finding schemes by a unique string in the name that does not exist.
     * <br>
     * Verifies that no schemes are found for a unique, non-existing name.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByName_UniqueStringNotFound() throws IOException {
        // Setup: Use a highly unique search string that is guaranteed not to be in any scheme names
        String searchName = "XYZ123NonExistingName";
        Scheme[] expectedSchemes = {}; // No schemes with this unique string in their names

        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByName(searchName);

        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes, "Expected an empty array when no schemes with the given unique string are found");
    }
    
    /**
     * Tests finding schemes where both the name and title match the search criteria.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByNameAndTitle_BothMatch() throws IOException {
        // Setup
        String searchName = "Dr. Freeze";
        String searchTitle = "Freeze America";
        Scheme[] expectedSchemes = {testSchemes[0]}; // Only this scheme has both name and title matching
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
    }

    /**
     * Tests finding schemes where only the name matches, but the title does not.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByNameAndTitle_OnlyNameMatches() throws IOException {
        // Setup
        String searchName = "Agental00";
        String searchTitle = "NonExistingTitle";
        Scheme[] expectedSchemes = {}; // No schemes should match as title doesn't match
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
    }

    /**
     * Tests finding schemes where only the title matches, but the name does not.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByNameAndTitle_OnlyTitleMatches() throws IOException {
        // Setup
        String searchName = "NonExistingName";
        String searchTitle = "Freeze America";
        Scheme[] expectedSchemes = {}; // No schemes should match as name doesn't match
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
    }

    /**
     * Tests finding schemes where neither the name nor the title matches.
     * 
     * @throws IOException if an input/output error occurs
     */
    @Test
    public void testFindSchemesByNameAndTitle_NeitherMatches() throws IOException {
        // Setup
        String searchName = "NonExistingName";
        String searchTitle = "NonExistingTitle";
        Scheme[] expectedSchemes = {}; // No schemes should match as neither name nor title matches
        
        // Invoke
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
        
        // Analyze
        assertArrayEquals(expectedSchemes, foundSchemes);
    }

    /**
     * Tests successful retrieval of a scheme by a specific name. This test ensures that
     * {@link VillainFileDAO#getScheme_str(String)} correctly retrieves a scheme when
     * provided with a valid name.
     * 
     * Verifies that the retrieved scheme is not null and checks if the ID and title of the
     * scheme match the expected values.
     */
    @Test
    public void testGetScheme_str_Success() {
        // Setup
        String searchName = "Dr. Freeze";
    
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme_str(searchName);
    
        // Analyze
        assertNotNull(scheme, "Expected to find a scheme with the name 'Dr. Freeze'.");
        assertEquals(99, scheme.getId(), "The ID of the retrieved scheme does not match the expected value.");
        assertEquals("Freeze America", scheme.getTitle(), "The title of the retrieved scheme does not match the expected value.");
    }
    
    /**
     * Tests the retrieval of a scheme with a name that does not exist. Ensures that
     * {@link VillainFileDAO#getScheme_str(String)} returns {@code null} when searching
     * for a non-existing name.
     */
    @Test
    public void testGetScheme_str_NonExistingName() {
        // Setup
        String searchName = "NonExistingName";
    
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme_str(searchName);
    
        // Analyze
        assertNull(scheme, "Expected null when trying to retrieve a scheme with a non-existing name.");
    }

    /**
     * Tests the retrieval of a scheme with a null name. Verifies that
     * {@link VillainFileDAO#getScheme_str(String)} returns {@code null} when called with a
     * null name, reflecting that no scheme can be found with a null identifier.
     */
    @Test
    public void testGetScheme_str_NullName() {
        // Setup
        String searchName = null;
    
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme_str(searchName);
    
        // Analyze
        assertNull(scheme, "Expected null when trying to retrieve a scheme with a null name.");
    }
    
    /**
     * Tests the behavior of {@link VillainFileDAO#findSchemes(String)} when called with a null input.
     * This indirectly tests the method's ability to return all schemes in the absence of a filter.
     *
     */
    @Test
    public void testFindSchemes_WithNullInput() {
        Scheme[] allSchemes = VillainFileDAO.findSchemes(null); // Assuming null should return all schemes
    
        assertEquals(testSchemes.length, allSchemes.length, "Expected all schemes to be returned when input is null.");
    }

    /**
     * Tests the filtering functionality of {@link VillainFileDAO#findSchemes(String)} by passing
     * a substring that should be contained within some scheme names.
     * <br>
     * Verifies that only schemes containing the specified substring in their names are returned.
     *
     */
    @Test
    public void testFindSchemes_Filtering() {
        String contains = "al"; // This string is present in the names of some test schemes
        Scheme[] expectedSchemes = {testSchemes[1], testSchemes[2]}; // Schemes containing "al"
    
        Scheme[] foundSchemes = VillainFileDAO.findSchemes(contains);
    
        assertArrayEquals(expectedSchemes, foundSchemes, "Expected to find schemes with names containing 'al'.");
    }
    
    /**
     * Tests finding schemes with both name and title parameters as null using
     * {@link VillainFileDAO#findSchemesByNameAndTitle(String, String)}.
     * This should return all schemes as it implies no filtering is applied.
     *
     * @throws IOException if an input/output error occurs during the operation
     */
    @Test
    public void testFindSchemesByNameAndTitle_BothNull() throws IOException {
        String searchName = null;
        String searchTitle = null;
        
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);

        assertEquals(testSchemes.length, foundSchemes.length, "Expected all schemes to be returned when both name and title are null.");
    }
    
    /**
     * Tests the case insensitivity of the {@link VillainFileDAO#findSchemesByNameAndTitle(String, String)}
     * method. Ensures that schemes can be found regardless of the case sensitivity of the name and title.
     *
     * @throws IOException if an input/output error occurs during the operation
     */
    @Test
    public void testFindSchemesByNameAndTitle_CaseInsensitivity() throws IOException {
        String searchName = "dr. freeze"; // Different case
        String searchTitle = "freeze america"; // Different case
        
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
        
        assertArrayEquals(new Scheme[]{testSchemes[0]}, foundSchemes, "Expected case-insensitive match for name and title.");
    }
    
    /**
     * Tests the ability of {@link VillainFileDAO#findSchemesByNameAndTitle(String, String)}
     * to handle special characters in the search for name and title.
     * Verifies that schemes can be found even when the search terms contain special characters.
     *
     * @throws IOException if an input/output error occurs during the operation
     */
    @Test
    public void testFindSchemesByNameAndTitle_SpecialCharacters() throws IOException {

        String searchName = "Dr.-Freeze"; // Assume the stored name includes special characters
        String searchTitle = "Freeze_America"; // Assume the stored title includes special characters
        
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
    }
    
    /**
     * Tests the behavior of {@link VillainFileDAO#findSchemesByNameAndTitle(String, String)}
     * when one of the parameters (name or title) is empty. This tests the method's ability to
     * filter schemes based on a non-empty parameter while ignoring the empty one.
     *
     * @throws IOException if an input/output error occurs during the operation
     */
    @Test
    public void testFindSchemesByNameAndTitle_EmptyNameOrTitle() throws IOException {
        String searchName = ""; // Empty name
        String searchTitle = "Freeze"; // Valid title search term
        
        Scheme[] foundSchemes = VillainFileDAO.findSchemesByNameAndTitle(searchName, searchTitle);
    }
    
}
