import styled from 'styled-components'
import React, {useState} from "react";
import MainLayout from "../../components/MainLayout";
import WhiteButton from "../../components/buttons/WhiteButtonBig";
import {API} from "../../libs/api";
import Projects from "../../components/Cards/Projects";
import Link from "next/link";
import Api from "../../api/api";
import Select from "react-select";
import {sortCards} from "../../scripts/Sort";

export default function ProjectsPage() {
    const getData = async () => await API.getProjects();
    const [specialization, setSpecialization] = useState();

    const [entities, setEntities] = React.useState([]);
    const [sorted, setSorted] = useState([]);

    React.useEffect(async () => {
        const {data} = await getData();
        setEntities(data);
        setSorted(data);
        const response = await Api.getCategoriesofSpecializations();
        setSpecialization(response.data.map(element => ({label: element?.name, value: element?.id})));
    }, []);

    const sort = (value) => {
        setSorted(sortCards(entities, null, value));
    };

    return (
        <MainLayout>
            <Head>
                <Title>Проекты</Title>
                <div>
                    <Select isClearable onChange={(value) => !value ? setSorted(entities) : sort(value)} placeholder='Выбрать' options={specialization}/>
                </div>
                <div>
                    <Link href='/projects/new'>
                        <a>
                            <WhiteButton text="Создать проект"/>
                        </a>
                    </Link>
                </div>


            </Head>
            <Container>
                <ProjectsCont>
                    {sorted.map(card =>
                        <Projects project={card} key={card.id}/>)}
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

