import styled from 'styled-components'
import React, {useEffect, useRef, useState} from "react";
import MainLayout from "../components/MainLayout";
import UserInfo from "../components/Cards/UserInfo";
import {API} from "../libs/api";
import Api from "../api/api";
import Select from "react-select";
import {sortUsers} from "../scripts/Sort";

export default function Home() {
    const [skills, setSkills] = useState();
    const [entities, setEntities] = React.useState([]);
    const [sorted, setSorted] = useState([]);

    useEffect( async () => {
        const skills = await API.getCategories();
        setSkills(skills.data.map(elem => ({value: elem.id, label: elem.name})))
    }, []);

    const getData = async () => await API.getUsers();


    React.useEffect(async () => {
        const {data} = await getData();
        setEntities(data)
        setSorted(data)
    }, []);

    const sortEntities = (value) => {
        console.log(value)
        if (value) {
            const sort = sortUsers(entities, null, value)
            console.log(sort)
            setSorted(sort)
        } else {
            setSorted(entities)
        }

    };

    return (
        <MainLayout>
            <Head>
                <Title>Специалисты</Title>
                <div>
                    <h2>Фильтр</h2>
                    <Select onChange={(value) => sortEntities(value?.label)} placeholder={'Выбрать'} isClearable   options={skills}/>
                </div>
            </Head>
            <Container>
                <ProjectsCont>
                    {sorted.map(card =>
                        <UserInfo
                            specialist={card}
                            key={card.id}
                        />)}
                </ProjectsCont>
            </Container>
        </MainLayout>
    )
}

const Head = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  margin-top: 90px;
`

const Title = styled.div`
  font-size: 44px;
  font-weight: 800;
`

const Container = styled.div`
  display: flex;
`

const ProjectsCont = styled.div`
  display: flex;
  flex-direction: column;
`

