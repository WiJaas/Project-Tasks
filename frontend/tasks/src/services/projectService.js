// src/services/projectService.js
import axiosClient from "./axiosClient";

export const getProjects = async () => {
  const res = await axiosClient.get("/projects");
  return res.data;
};

export const getProject = async (id) => {
  const res = await axiosClient.get(`/projects/${id}`);
  return res.data;
};


export const createProject = async (data) => {
  const res = await axiosClient.post("/projects", data);
  return res.data;
};

export const updateProject = async (id, data) => {
  const res = await axiosClient.put(`/projects/${id}`, data);
  return res.data;
};

export const deleteProject = async (id) => {
  await axiosClient.delete(`/projects/${id}`);
};
