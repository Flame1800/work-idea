import styled from "styled-components";
import React from "react";
import WhiteButton from "../buttons/WhiteButton";
import BlackButton from "../buttons/BlackButton";
import {API} from "../../libs/api";
import {useAppDispatch, useAppSelector} from "../../redux/hooks";
import {useRouter} from "next/router";
import {addComment} from "../../redux/slices/comment";


export default function TextInput({ project }) {
    const [info, setInfo] = React.useState("")
    const dispatch = useAppDispatch()
    const user = useAppSelector(state => state.user)
    const router = useRouter()

    const sendRequest = async () => {
        if (user.id === null) {
            return router.push('/login')
        }

        const form = {
            users_permissions_user: user.id,
            comment: info,
            project: project.id
        }

        const comment = await API.sendComment(form)
        setInfo('')
        dispatch(addComment(comment.data))
    }

    return (
        <Flex>
            <Textarea
                value={info}
                onChange={({target}) => setInfo(target.value)}
                placeholder={'Начните писать здесь...'}
            />
            <div onClick={() => sendRequest()}>
                <BlackButton text="Отправить" />
            </div>
        </Flex>

    );
}

const Flex = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`

const Textarea = styled.textarea`
  border: 1px solid #000;
  min-height: 200px;
  border-radius: 10px;
  width: 700px;
  padding-top: 20px;
  padding-left: 20px;
  font-size: 20px;
  margin-bottom: 10px;
;
`

