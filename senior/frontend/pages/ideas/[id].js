import styled from 'styled-components'
import React from "react";
import MainLayout from "../../components/MainLayout";
import OrangeButton from "../../components/buttons/OrangeButton";
import {API} from "../../libs/api";
import CommentsBlock from "../../components/Comments";
import SendRealizeIdea from "../../components/SendRealizeIdea";

function Idea({data}) {

    const realize = async () => {
        const project = await API.realize({
            idea: data.id,
            specialization: 1,
            "users_permissions_user": 1
        })
    }

    return (
        <MainLayout>
            <Head>
                <Title>Идея</Title>
            </Head>
            <Flex>
                <div>
                    <Person>
                        <img src="/images/avatar-2.png" height={50} />
                        <NameCont>
                            <p>
                                <Name>{data.users_permissions_user?.username}</Name>
                                <Desc>Редактировать статус</Desc>
                            </p>
                        </NameCont>
                    </Person>
                    <Content>
                        <ProjectTitle>{data.minifiedDescription}</ProjectTitle>
                        <ProjectDesc>
                            {data.fullDescription}
                        </ProjectDesc>
                    </Content>
                   <SendRealizeIdea idea={data} />
                    <CommentsBlock project={data} idea={true} />
                </div>
            </Flex>

        </MainLayout>
    )
}

export async function getServerSideProps(context) {
    const {data} = await API.getIdea(context.query.id)

    return {
        props: { data }, // will be passed to the page component as props
    }
}

export default Idea

const Head = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  margin-top: 90px;
`

const Flex = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`

const Users  = styled.div`
  display: flex;
  flex-direction: column;
`

const Title = styled.div`
  font-size: 44px;
  font-weight: 800;
`

const Person = styled.div`
  display: flex;
  margin-bottom: 30px;
   img {
     margin-top: 20px;
   }
`

const NameCont = styled.div`
  margin-left: 30px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`

const  Name = styled.div`
  font-size: 36px;
  font-weight: 800;
  margin-bottom: 10px;
`

const Desc = styled.div`
  font-size: 18px;
  color: #474A57;
  font-weight: 500;
`

const Skills = styled.div`
  max-width: 800px;
`

const SkillTitle = styled.div`
  font-size: 18px;
  font-weight: 600;
  color: #474A57;
  margin-bottom: 10px;
`
const Items = styled.div`
  font-size: 24px;
  font-weight: 500;
`

const Content = styled.div`
  max-width: 800px;
  margin-top: 60px;
`

const ProjectTitle = styled.div`
  font-size: 36px;
  font-weight: 800;
  margin-bottom: 10px;
`


const ProjectDesc = styled.div`
  font-size: 23px;
  font-weight: 300;
  color: #474A57;
  margin-bottom: 40px;
`