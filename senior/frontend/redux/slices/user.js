import { createSlice } from "@reduxjs/toolkit";

const placeholderUser = {
    id: null,
    username: null,
    email: null,
    About: null,
    "categories_of_specializations": [

    ],
    "projects": [

    ]
}

const userSlice = createSlice({
    name: "users",
    initialState: placeholderUser,
    reducers: {
        setUser(state, action) {
            return action.payload
        },
        clearUser(state, action) {
            state = null;
        }
    },
});

export const { setUser, clearUser } =
    userSlice.actions;

export const userReducer = userSlice.reducer;