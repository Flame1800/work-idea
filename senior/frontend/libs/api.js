import axios from "axios";

export const API = {}

const url = 'http://193.168.48.37:1337'

API.getProjects = () => axios(`${url}/projects`)
API.like = (user) => axios.put(`${url}/user/${user.id}`, user)

API.getProject = (id) => axios(`${url}/projects/${id}`)
API.createProject = (project) => axios.post(`${url}/projects`, project)

API.addSpecs = (form) => axios.post(`${url}/project-needed-specializations`, form)

API.getIdeas = () => axios(`${url}/ideas`)
API.getIdea = (id) => axios(`${url}/ideas/${id}`)
API.realize = (form) => axios.post(`${url}/ready-to-realize-users`, form)

API.getUsers = () => axios(`${url}/users`)

API.getCategories = () => axios(`${url}/Categories-of-specializations`)
API.getNeededSpecs = () => axios(`${url}/project-needed-specializations`)
API.getParticipants = () => axios(`${url}/project-participants`)
API.addParticipant = (form) => axios.post(`${url}/project-participants`, form)

API.getUser = (id) => axios(`${url}/users/${id}`)

API.getRequests = () => axios(`${url}/participation-in-the-project-requests`)
API.getSpecs = () => axios(`${url}/specializations`)

API.acceptRequest = (id) => axios.put(`${url}/participation-in-the-project-requests/${id}`, {"requestAccepted": true})
API.closeRequest = (id) => axios.delete(`${url}/participation-in-the-project-requests/${id}`)
API.sendRequest = (form) => axios.post(`${url}/participation-in-the-project-requests`, form)
API.sendComment = (form) => axios.post(`${url}/project-comments`, form)
API.getProjectComments = () => axios(`${url}/project-comments`)
API.getIdeaComments = () => axios(`${url}/idea-comments`)

API.createUser = (user) => axios.post(`${url}/users`, user)
API.login = (user) => axios.post(`${url}/auth/local`, user)

API.sendBlank = (form, id) => axios.put(`${url}/users/${id}`, form)