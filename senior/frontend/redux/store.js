import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit'
import { userReducer} from './slices/user';
import {commentReducer} from "./slices/comment";


export function makeStore() {
    return configureStore({
        reducer: {
            user: userReducer,
            comments: commentReducer
        },
        devTools: process.env.NODE_ENV !== 'production'
    })
}

export const store = makeStore()

