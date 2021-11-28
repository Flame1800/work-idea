import styled from "styled-components";
import React from "react";
import Select from "react-select";
import OrangeButton from "./buttons/OrangeButton";
import {API} from "../libs/api";
import {useAppSelector} from "../redux/hooks";


export default function SendRequestFrom({ specs, project, requests }) {
    const user = useAppSelector(state => state.user)
    const [entities, setEntities] = React.useState([])
    const [selected, setSelected] = React.useState(null)
    const [status, setStatus] = React.useState(null)

    React.useEffect(async () => {
        const res = specs.map(item => {
            return {
                value: item.specialization.name,
                label: item.specialization.name,
                ...item
            }
        })
        setEntities(res)
    }, [])

    const sendRequest = async () => {
        const form = {
            users_permissions_user: user.id,
            project: project.id,
            specialization: selected.id,
            projectAuthorRequest: false
        }

        await API.sendRequest(form)
        setStatus('send')
    }

    if (status === 'send' || requests.map(req => req.users_permissions_user.id).includes(user.id)) {
        return <TextGreen>Заявка отправленна. Ожидание ответа от автора</TextGreen>
    }

    return (
       <>
           <Text>Выберите навык и <br /> откликнитесь на задачу</Text>
           <Select
               placeholder={'Выбрать'}
               options={entities}

               isClearable
               onChange={(values) => setSelected(values)}
               value={selected}
           />
           <br />
           {!selected && <Btn>Откликнуться</Btn>}
           {selected && <div onClick={() => sendRequest()}>
               <OrangeButton text='Откликнуться' />
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
const TextGreen = styled.div`
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  margin-top: 50px;
  color: #00C6AE;
`
const Btn = styled.div`
  display: flex;
  background-color: #ff855e;
  color: white;
  padding: 16px;
  width: 170px;
  height: auto;
  border-radius: 12px;
  text-align: center;
  text-decoration: none;
  align-self: center;
  box-shadow: 0px 6px 0px #18191F;
  font-size: 21px;
  font-weight: 800;
  cursor: not-allowed;
`