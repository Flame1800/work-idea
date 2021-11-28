import React from "react";
import styled from "styled-components"
import Select from 'react-select'
import OrangeButton from "../../buttons/OrangeButton";
import {API} from "../../../libs/api";
import {useAppDispatch, useAppSelector} from "../../../redux/hooks";
import {setUser} from "../../../redux/slices/user";


export default function Blank({entity}) {
    const getData = async () => await API.getCategories()
    const user = useAppSelector(state => state.user)
    const dispatch = useAppDispatch()

    const categories = user.categories_of_specializations.map(item => {
        return {
            value: item.name,
            label: item.name,
            ...item
        }
    })

    const [entities, setEntities] = React.useState([])
    const [selected, setSelected] = React.useState(categories)
    const [info, setInfo] = React.useState(user.About)

    React.useEffect(async () => {
        const {data} = await getData()
        const res = data.map(item => {
            return {
                value: item.name,
                label: item.name,
                ...item
            }
        })
        setEntities(res)
    }, [])

    const send = async () => {
        const form = {
            About: info,
            categories_of_specializations: selected
        }

        const upUser = await API.sendBlank(form, user.id)
        dispatch(setUser(upUser.data))
    }


    return (
        <Wrapper>
            <Header>Моя анкета</Header>
            {entity.id === user.id ?
                <Form>
                    <Text>Интересные направления</Text>
                    <Select
                        placeholder={'Выбрать'}
                        options={entities}
                        isMulti
                        isClearable
                        onChange={(values) => setSelected(values)}
                        value={selected}
                    />
                    <Text>Текст анкеты</Text>

                    <Textarea value={info} onChange={({target}) => setInfo(target.value)} placeholder={'Напишите о себе'}  />
                    <div onClick={() => send()}>
                        <OrangeButton text='Сохранить' />
                    </div>
                </Form>
                :
                <Form>
                    <Text>Интересные направления</Text>
                    {entity.categories_of_specializations.map(c => <TextSmall>{c.name}<br /></TextSmall>)}
                    <Text>О себе</Text>
                    <TextSmall>{entity.About ? entity.About : 'нет информации'}</TextSmall>
                </Form>
            }

        </Wrapper>
    )
}

const Wrapper = styled.div`
  max-width: 1300px;
  margin: 30px auto;
  border: 1px solid #000;
  border-radius: 10px 10px 0 0;
  min-height: 500px;
`;

const TextSmall = styled.div`
  font-size: 24px;
`

const Header = styled.div`
  font-size: 24px;
  font-weight: 800;
  padding: 10px;
  border-bottom: 1px solid #000;
`;

const Form = styled.div`
  max-width: 800px;
  margin: 40px auto;
`;

const Text = styled.div`
  font-size: 27px;
  font-weight: 800;
  margin-bottom: 20px;
  margin-top: 50px;
`


const Textarea = styled.textarea`
  border: 1px solid #000;
  width: 100%;
  min-height: 200px;
  border-radius: 10px;
  padding-top: 20px;
  padding-left: 20px;
  font-size: 20px;
  margin-bottom: 40px;
;
`




