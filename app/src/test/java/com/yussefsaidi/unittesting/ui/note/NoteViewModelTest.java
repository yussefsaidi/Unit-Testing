package com.yussefsaidi.unittesting.ui.note;

import com.yussefsaidi.unittesting.models.Note;
import com.yussefsaidi.unittesting.repository.NoteRepository;
import com.yussefsaidi.unittesting.ui.Resource;
import com.yussefsaidi.unittesting.util.InstantExecutorExtension;
import com.yussefsaidi.unittesting.util.LiveDataTestUtil;
import com.yussefsaidi.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.yussefsaidi.unittesting.repository.NoteRepository.INSERT_SUCCESS;

@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {

    // system under test
    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

    /*
        can't observe a note that hasn't been set
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception{
        // Arrange
        LiveDataTestUtil<Note> liveDataTestUtil= new LiveDataTestUtil<>();

        // Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        Assertions.assertNull(note);
    }

    /*
        Observe a note has been set and onChanged will trigger in activity
     */
    @Test
    void observeNote_whenSet() throws Exception{
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil= new LiveDataTestUtil<>();

        // Act
        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        Assertions.assertEquals(note, observedNote);
    }

    /*
        Insert a new note and observe row returned
     */
    @Test
    void insertNote_returnRow() throws Exception{
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, INSERT_SUCCESS));
        Mockito.when(noteRepository.insertNote(Mockito.any(Note.class))).thenReturn(returnedData);

        // Act
        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());


        // Assert
        Assertions.assertEquals(Resource.success(insertedRow, INSERT_SUCCESS), returnedValue);
    }

    /*
        insert: don't return a new row without observer
     */
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception{
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Act
        noteViewModel.setNote(note);

        // Assert
        Mockito.verify(noteRepository, Mockito.never()).insertNote(Mockito.any(Note.class));
    }

    /*
        set note, null title, throw exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception{
        // Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // Assert
        Assertions.assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noteViewModel.setNote(note);
            }
        });
    }
}
