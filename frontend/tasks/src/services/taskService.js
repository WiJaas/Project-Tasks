import axiosClient from "./axiosClient";

export const createTask = async (projectId, data) => {
  const res = await axiosClient.post(
    `/projects/${projectId}/tasks`,
    data
  );
  return res.data;
};
export const updateTask = async (projectId, taskId, data) => {
  const res = await axiosClient.put(
    `/projects/${projectId}/tasks/${taskId}`,
    data
  );
  return res.data;
};

export const completeTask = async (projectId, taskId) => {
  await axiosClient.patch(
    `/projects/${projectId}/tasks/${taskId}/complete`
  );
};
export const deleteTask = async (projectId, taskId) => {
  await axiosClient.delete(
    `/projects/${projectId}/tasks/${taskId}`
  );
};

export const getTasks = async (projectId, page = 0, size = 5) => {
  const res = await axiosClient.get(
    `/projects/${projectId}/tasks?page=${page}&size=${size}`
  );
  return res.data;
};
