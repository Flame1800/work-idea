import styled from 'styled-components'
import React, {useEffect, useRef, useState} from "react";
import MainLayout from "../../components/MainLayout";
import OrangeButton from "../../components/buttons/OrangeButton";
import Api from '../../api/api.js';
import Select from "react-select";
import {useAppSelector} from "../../redux/hooks";
import {useRouter} from "next/router";
import {API} from "../../libs/api";
import CommentCard from "../../components/Comments";

export default function Project() {
    const user = useAppSelector(state => state.user)

    const router = useRouter()

    React.useEffect(() => {
        if (user.username === null) {
            router.push('/login')
        }
    }, [])

    const [category, setCategory] = useState([]);
    const [selectedCat, setSelectedCat] = useState(null);
    const [selectedSpec, setSelectedSpec] = useState(null);
    const [specs, setSpecs] = useState([]);
    const title = useRef();
    const text = useRef();

    useEffect(async () => {
        const response = await API.getCategories()
        const responceSpecs = await API.getSpecs()
        setCategory(response.data.map(elem => ({id: elem.id, label: elem.name, value: elem.name, ...elem})));
        setSpecs(responceSpecs.data.map(elem => ({id: elem.id, label: elem.name, value: elem.name, ...elem})));
    }, []);

    const sendForm = async () => {

        const {data} = await Api.createProject({
            Author: user.id,
            name: title.current.value,
            category: selectedCat,
            description: text.current.value,
        });

        selectedSpec.map(async spec => {
            await API.addSpecs({
                specialization: spec.id,
                project: data.id
            })
        })


        title.current.value = '';
        text.current.value = '';
        router.push(`/projects/${data.id}`)
    };

    return (
        <MainLayout>
            <Head>
                <Title>Создать проект</Title>
            </Head>
            <Form>
                <Text>Направления</Text>
                <Select placeholder={'Выбрать'} onChange={(value) => setSelectedCat(value)} options={category}/>

                {selectedCat && <>
                    <Text>Какие нужны специалисты</Text>
                    <Select
                        onChange={(value) => setSelectedSpec(value)}
                        value={selectedSpec}
                        placeholder={'Выбрать'}
                        options={specs.filter(spec => spec?.category?.id === selectedCat.id)}
                        isMulti
                        isClearable
                    />
                </>}
                <Text>Название</Text>
                <Titlearea ref={title} placeholder={'Введите название'}/>
                <Text>Описание</Text>
                <Textarea ref={text} placeholder={'Напишите о своей идее'}/>
                <div onClick={() => sendForm()}>
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
