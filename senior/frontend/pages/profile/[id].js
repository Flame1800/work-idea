import styled from 'styled-components'
import React from "react";
import Header from "../../components/Header";
import MainLayout from "../../components/MainLayout";
import ProfileBar from "../../components/Profile/Bar/ProfileBar";
import MainContent from "../../components/Profile/MainContent/MainContent";
import {API} from "../../libs/api";
import {useAppSelector} from "../../redux/hooks";
import {useRouter} from "next/router";

export default function User({ entity }) {
    const user = useAppSelector(state => state.user)

    return (
        <MainLayout>
            <ProfileBar  entity={entity} />
            <MainContent  entity={entity} />
        </MainLayout>
    )
}

export async function getServerSideProps(context) {
    const {data} = await API.getUser(context.query.id)


    return {
        props: {
           entity: data,
        },
    }
}

const Wrapper = styled.div`

`

