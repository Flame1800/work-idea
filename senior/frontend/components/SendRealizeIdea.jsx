import styled from "styled-components";
import React from "react";
import Select from "react-select";
import OrangeButton from "./buttons/OrangeButton";
import {API} from "../libs/api";
import {useAppSelector} from "../redux/hooks";
import {useRouter} from "next/router";


export default function SendRealizeIdea({ idea }) {
    const user = useAppSelector(state => state.user)
    const [entities, setEntities] = React.useState([])
    const [selected, setSelected] = React.useState(null)
    const [status, setStatus] = React.useState(null)
    const router = useRouter()

    React.useEffect(async () => {
        const specs = await API.getCategories()
        console.log(specs.data)
        const res = specs.data.map(item => {
            return {
                value: item?.name,
                label: item?.name,
                ...item
            }
        })
        setEntities(res)
    }, [])

    const sendRequest = async () => {
        if (user.id === null) {
            router.push(`/login`)
        }

        const form = {
            users_permissions_user: user.id,
            idea: idea.id,
            specialization: selected.id,
        }

        const {data} = await API.realize(form)

        const newProject = await API.createProject({
            idea: data.idea.id,
            Author: user.id,
            name: data.idea.minifiedDescription,
            category: selected.id,
            description: data.idea.fullDescription,
        })
        console.log(newProject, form)
        router.push(`/projects/${newProject.data.id}`)
        setStatus('send')
    }

    if (status === 'send') {
        return "Заявка отправленна"
    }

    return (
       <>
           <Text>Выберите направление и <br /> реализуйте идею</Text>
           <Select
               placeholder={'Выбрать'}
               options={entities}

               isClearable
               onChange={(values) => setSelected(values)}
               value={selected}
           />
           <br />
           {selected && <div onClick={() => sendRequest()}>
               <OrangeButton text='Реализовать идею' />
           </div>}
       </>
    );
}


const Text = styled.div`
  font-size: 27px;
  font-weight: 800;
  margin-bottom: 20px;
  margin-top: 50px;
`
