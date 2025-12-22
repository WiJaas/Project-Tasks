// src/pages/ProjectDetails.jsx
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import { getProject } from "../services/projectService";
import {
  getTasks,
  completeTask,
  deleteTask,
} from "../services/taskService";

import TaskFormModal from "../components/TaskFormModal";
import "../App.css";

export default function ProjectDetails() {
  const { projectId } = useParams();
  const navigate = useNavigate();

  const [project, setProject] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [showTaskModal, setShowTaskModal] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);

  const fetchProject = async () => {
    try {
      const projectData = await getProject(projectId);
      setProject(projectData);
    } catch (err) {
      console.error(err);
      alert("Failed to load project.");
    }
  };

  const fetchTasks = async () => {
    try {
      const data = await getTasks(projectId, page, 5);
      setTasks(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProject();
  }, [projectId]);

  useEffect(() => {
    fetchTasks();
  }, [page]);

  const openCreateTask = () => {
    setSelectedTask(null);
    setShowTaskModal(true);
  };

  const openEditTask = (task) => {
    setSelectedTask(task);
    setShowTaskModal(true);
  };

  const handleTaskSaved = () => {
    setShowTaskModal(false);
    setPage(0);
    fetchTasks();
    fetchProject();
  };

  const handleCompleteTask = async (taskId) => {
    try {
      await completeTask(projectId, taskId);
      fetchTasks();
      fetchProject();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDeleteTask = async (taskId) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this task?"
    );

    if (!confirmed) return;

    try {
      await deleteTask(projectId, taskId);
      fetchTasks();
      fetchProject();
    } catch (err) {
      console.error(err);
      alert("Failed to delete task");
    }
  };

  if (!project)
    return (
      <div className="flex items-center justify-center py-20 text-slate-500">
        Loading...
      </div>
    );
    const isOverdue = (dueDate, status) => {
  if (!dueDate || status === "COMPLETED") return false;

  const today = new Date();
  today.setHours(0, 0, 0, 0);

  return new Date(dueDate) < today;
};


  const { totalTasks, completedTasks, progressPercentage } = project;

  return (
    <div className="min-h-screen bg-slate-50 px-6 py-8">
      <div className="mx-auto max-w-5xl space-y-8">
        {/* Project Card */}
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <button
            className="mb-4 text-sm text-slate-500 hover:text-slate-800 transition"
            onClick={() => navigate(-1)}
          >
            ← Back
          </button>

          <h1 className="text-2xl font-semibold text-slate-800">
            {project.title}
          </h1>

          <p className="mt-1 text-slate-500">
            {project.description}
          </p>

          {/* Progress */}
          <div className="mt-6 space-y-2">
            <p className="text-sm text-slate-600">
              Progress: {completedTasks} / {totalTasks} tasks completed
              <span className="ml-1 text-slate-500">
                ({progressPercentage}%)
              </span>
            </p>

            <div className="h-2 w-full overflow-hidden rounded-full bg-slate-200">
              <div
                className="h-full rounded-full bg-blue-600 transition-all"
                style={{ width: `${progressPercentage}%` }}
              />
            </div>
          </div>

          <button
            className="mt-6 inline-flex items-center rounded-lg
                       bg-blue-600 px-5 py-2.5 text-sm font-medium
                       text-white hover:bg-blue-700 transition"
            onClick={openCreateTask}
          >
            + Add Task
          </button>
        </div>

        {/* Tasks */}
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <h3 className="mb-4 text-lg font-semibold text-slate-800">
            Task List
          </h3>

          <ul className="divide-y divide-slate-200">
            {tasks.map((task) => (
              <li
                key={task.id}
                className="flex items-center justify-between py-4"
              >
                {/* Left */}
                <div className="flex items-center gap-4">
                  <button
                    type="button"
                    disabled={task.status === "COMPLETED"}
                    onClick={() => handleCompleteTask(task.id)}
                    className={`h-5 w-5 rounded-full border flex items-center justify-center
                      ${
                        task.status === "COMPLETED"
                          ? "border-green-500 bg-green-500"
                          : "border-slate-300 hover:border-blue-500"
                      }`}
                  >
                    {task.status === "COMPLETED" && (
                      <span className="h-2.5 w-2.5 rounded-full bg-white" />
                    )}
                  </button>

                 <div className="flex flex-col">
  <span
    className={`text-sm font-medium ${
      task.status === "COMPLETED"
        ? "text-slate-400 line-through"
        : isOverdue(task.dueDate, task.status)
        ? "text-red-600"
        : "text-slate-700"
    }`}
  >
    {task.title}
    <span className="ml-1 text-xs text-slate-400">
      ({task.status})
    </span>
  </span>

  {/* Due date */}
  <span
    className={`text-xs mt-0.5 ${
      isOverdue(task.dueDate, task.status)
        ? "text-red-600 font-medium"
        : "text-slate-400"
    }`}
  >
    Due:{" "}
    {new Date(task.dueDate).toLocaleDateString()}
  </span>
</div>

                </div>

                {/* Actions */}
                <div className="flex gap-4">
                  {task.status !== "COMPLETED" && (
                    <button
                      className="text-sm text-blue-600 hover:underline"
                      onClick={() => openEditTask(task)}
                    >
                      Edit
                    </button>
                  )}

                  <button
                    className="text-sm text-red-600 hover:underline"
                    onClick={() => handleDeleteTask(task.id)}
                  >
                    Delete
                  </button>
                </div>
              </li>
            ))}
          </ul>

          {/* Pagination */}
          <div className="mt-6 flex items-center justify-between text-sm">
            <button
              disabled={page === 0}
              onClick={() => setPage((p) => p - 1)}
              className="rounded-lg border border-slate-300 px-4 py-1.5
                         disabled:opacity-50"
            >
              Previous
            </button>

            <span className="text-slate-500">
              Page {page + 1} / {totalPages}
            </span>

            <button
              disabled={page + 1 >= totalPages}
              onClick={() => setPage((p) => p + 1)}
              className="rounded-lg border border-slate-300 px-4 py-1.5
                         disabled:opacity-50"
            >
              Next
            </button>
          </div>
        </div>

        {/* Modal */}
        {showTaskModal && (
          <TaskFormModal
            projectId={projectId}
            task={selectedTask}
            onClose={() => setShowTaskModal(false)}
            onSaved={handleTaskSaved}
          />
        )}
      </div>
    </div>
  );
}
