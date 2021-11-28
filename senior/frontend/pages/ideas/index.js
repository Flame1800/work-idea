import styled from 'styled-components'
import React from "react";
import MainLayout from "../../components/MainLayout";
import WhiteButton from "../../components/buttons/WhiteButtonBig";
import Ideas from "../../components/Cards/Ideas";
import {API} from "../../libs/api";
import Link from "next/link";

export default function IdeasPage() {
    const getData = async () => await API.getIdeas()

    const [entities, setEntities] = React.useState([])

    React.useEffect(async () => {
        const {data} = await getData()
        setEntities(data)
    }, [])

    console.log(entities)


    return (
        <MainLayout>
            <Head>
                <Title>Идеи</Title>
                <Link href='/ideas/new' >
                    <a>
                        <WhiteButton text="Предложить идею" />
                    </a>
                </Link>
            </Head>
            <Container>
                <ProjectsCont>
                    {entities.map(card => <Ideas idea={card} key={card.id} />)}
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

