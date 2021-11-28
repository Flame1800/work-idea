import { createSlice } from "@reduxjs/toolkit";

const commentSlice = createSlice({
    name: "comments",
    initialState: [],
    reducers: {
        setComments(state, action) {
            return action.payload
        },
        addComment(state, action) {
            return [action.payload, ...state]
        }
    },
});

export const { setComments, addComment } =
    commentSlice.actions;

export const commentReducer = commentSlice.reducer;