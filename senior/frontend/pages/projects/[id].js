import styled from 'styled-components'
import React from "react";
import MainLayout from "../../components/MainLayout";
import User from "../../components/Cards/User";
import {useRouter} from "next/router";
import {API} from "../../libs/api";
import {useAppSelector} from "../../redux/hooks";
import RequsetCard from '../../components/Cards/RequestCard'
import SendRequestFrom from "../../components/SendRequestFrom";
import CommentsBlock from "../../components/Comments/index";

export default function Project({data, specs, users, requests}) {
    const user = useAppSelector(state => state.user)

    return (
        <MainLayout>
            <Head>
                <Title>Проект <div> {data?.category?.name}</div></Title>
            </Head>
            <Flex>
                <div>
                    <Person>
                        <img src="/images/avatar-2.png" height={50} />
                        <NameCont>
                            <p>
                                <Name>{data.Author?.username}</Name>
                                <Desc>{data.Author?.About}</Desc>
                            </p>
                        </NameCont>
                    </Person>
                    {user.id === data?.Author?.id && user.id !== null
                    &&
                    <>
                    <TitleUsers>Заявки: {requests.length}</TitleUsers>
                    <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                        {requests.map(request =>
                            <RequsetCard
                                key={request.id}
                                request={true}
                                participant={request}
                            />)}
                    </div>
                    </>}
                    <Skills>
                        <SkillTitle>Требуемые навыки</SkillTitle>
                        <Items>
                            {specs.map(item => `${item.specialization.name}, `)}
                       </Items>
                    </Skills>
                    <Content>
                        <ProjectTitle>{data.name}</ProjectTitle>
                        <ProjectDesc>
                            {data.description}
                        </ProjectDesc>
                    </Content>
                    {user.id !== data?.Author?.id
                    && users.map(u => u?.user?.id).indexOf(user.id) === -1
                    && user.id !== null
                    &&
                    <SendRequestFrom specs={specs} project={data} users={users} requests={requests} />}
                    <CommentsBlock project={data} />
                </div>
                <Users>
                    <TitleUsers>Участники: {users.length}</TitleUsers>
                    <div>
                        {users.map(card => <User key={card.id} user={card.user ? card : null} />)}
                    </div>
                </Users>
            </Flex>

        </MainLayout>
    )
}

export async function getServerSideProps(context) {
    const {data} = await API.getProject(context.query.id)
    const specs = await API.getNeededSpecs()
    const users = await API.getParticipants()
    const requests = await API.getRequests()

    return {
        props: {
            data,
            users: users.data.filter(user => user?.project?.id === data.id),
            specs: specs.data.filter(spec => spec?.project?.id === data.id),
            requests: requests.data.filter(spec => spec?.project?.id === data.id && !spec.requestAccepted)
        },
    }
}

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
  display: flex;
  align-items: center;

  div {
    margin-left: 44px;
    font-size: 40px;
    color: #626262;
    margin-top: 5px;
  }
`

const TitleUsers = styled.div`
  font-size: 27px;
  font-weight: 800;
  margin-bottom: 20px;
  margin-top: 30px;
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