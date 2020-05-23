package com.yussefsaidi.unittesting.repository;

import com.yussefsaidi.unittesting.models.Note;
import com.yussefsaidi.unittesting.persistence.NoteDao;
import com.yussefsaidi.unittesting.ui.Resource;
import com.yussefsaidi.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static com.yussefsaidi.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.yussefsaidi.unittesting.repository.NoteRepository.UPDATE_FAILURE;
import static com.yussefsaidi.unittesting.repository.NoteRepository.UPDATE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    // system under test
    private NoteRepository noteRepository;

    @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        MockitoAnnotations.initMocks(this);
        noteRepository = new NoteRepository(noteDao);
    }

    /*
        insert note
        verify correct method is called
        confirm observer is triggered
        confirm new rows are inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue.data);
        Assertions.assertEquals(Resource.success(1, NoteRepository.INSERT_SUCCESS), returnedValue);
    }
    
    /*
        insert note
        Failure (return -1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {
        //Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue.data);
        Assertions.assertEquals(Resource.error(null, NoteRepository.INSERT_FAILURE), returnedValue);
    }

    /*
        insert note
        null title
        confirm throws exception
     */
    @Test
    void insertNote__nullTitle_throwException() throws Exception {

        Exception exception = Assertions.assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        Assertions.assertEquals(NOTE_TITLE_NULL, exception.getMessage());

    }


    /*
        update note
        verify correct method is called
        confirm the observer is triggered
        confirm number of rows updated
     */
    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {

        // Arrange
        final int updatedRow = 1;
        Mockito.when(noteDao.updateNote(Mockito.any(Note.class))).thenReturn(Single.just(updatedRow));

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(Mockito.any(Note.class));
        verifyNoMoreInteractions(noteDao);

        Assertions.assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);

    }

    /*
        update note
        failure (-1)
     */
    @Test
    void updateNote_returnFailure() throws Exception {

        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        Mockito.when(noteDao.updateNote(Mockito.any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(Mockito.any(Note.class));
        verifyNoMoreInteractions(noteDao);

        Assertions.assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);

    }

    /*
        update note
        null title
        throw exception
     */
    @Test
    void updateNote__nullTitle_throwException() throws Exception {

        Exception exception = Assertions.assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        Assertions.assertEquals(NOTE_TITLE_NULL, exception.getMessage());

    }


}


















