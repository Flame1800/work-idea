import styled from 'styled-components'
import React, {useEffect, useRef, useState} from "react";
import MainLayout from "../../components/MainLayout";
import OrangeButton from "../../components/buttons/OrangeButton";
import Api from '../../api/api.js';
import Select from "react-select";
import {useAppSelector} from "../../redux/hooks";
import {useRouter} from "next/router";

export default function Project() {
    const user = useAppSelector(state => state.user)
    const title = useRef();
    const text = useRef();
    const router = useRouter()

    const sendForm = () => {
        Api.createIdea({
            Author: user.id,
            minifiedDescription: title.current.value,
            category: 1,
            fullDescription: text.current.value,
        })
            .then(resolve => {
                Api.createParticipants(resolve.data.id, user.id , 1)
                router.push(`/ideas/${resolve.data.id}`)
            });

    };

    return (
        <MainLayout>
            <Head>
                <Title>Предложить идею</Title>
            </Head>
            <Form>
                <Text>Название</Text>
                <Titlearea ref={title} placeholder={'Введите название'}/>
                <Text>Описание</Text>
                <Textarea ref={text} placeholder={'Напишите о своей идее'}/>
                <div onClick={sendForm}>
                    <OrangeButton text='Продолжить' />
                </div>
            </Form>

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


const Form = styled.div`
  max-width: 800px;
  margin: 40px auto;
`;

const Text = styled.div`
  font-size: 27px;
  font-weight: 800;
  margin-bottom: 20px;
  margin-top: 50px;
`;

const Titlearea = styled.textarea`
  border: 1px solid #000;
  width: 100%;
  min-height: 20px;
  border-radius: 10px;
  padding-top: 20px;
  padding-left: 20px;
  font-size: 20px;
  margin-bottom: 40px;
`;

const Textarea = styled.textarea`
  border: 1px solid #000;
  width: 100%;
  min-height: 200px;
  border-radius: 10px;
  padding-top: 20px;
  padding-left: 20px;
  font-size: 20px;
  margin-bottom: 40px;
`;