import { useEffect, useState } from "react";
import { createTask, updateTask } from "../services/taskService";

export default function TaskFormModal({
  projectId,
  task,
  onClose,
  onSaved,
}) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    if (task) {
      setTitle(task.title || "");
      setDescription(task.description || "");
      setDueDate(task.dueDate || "");
    } else {
      setTitle("");
      setDescription("");
      setDueDate("");
    }
    setError("");
  }, [task]);

  // 🔹 Helper: check if due date is in the past
  const isPastDate = (dateStr) => {
    if (!dateStr) return false;
    const selected = new Date(dateStr);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return selected < today;
  };

  const isFormValid =
    title.trim().length > 0 &&
    description.trim().length > 0 &&
    dueDate &&
    !isPastDate(dueDate);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!title.trim() || !description.trim() || !dueDate) {
      setError("Title, description, and due date are required.");
      return;
    }

    if (isPastDate(dueDate)) {
      setError("Due date cannot be in the past. Please select a new date.");
      return;
    }

    try {
      const payload = {
        title: title.trim(),
        description: description.trim(),
        dueDate,
      };

      const savedTask = task
        ? await updateTask(projectId, task.id, payload)
        : await createTask(projectId, payload);

      onSaved(savedTask);
      onClose();
    } catch (err) {
      console.error(err);
      setError("Failed to save task. Please try again.");
    }
  };

  return (
    <div
      className="fixed inset-0 z-[9999] flex items-center justify-center
                 bg-black/40 backdrop-blur-sm px-4"
    >
      <div className="w-full max-w-md rounded-2xl bg-white p-8 shadow-2xl">
        <h3 className="mb-6 text-xl font-semibold text-slate-800">
          {task ? "Edit Task" : "Create Task"}
        </h3>

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Title */}
          <input
            required
            placeholder="Task title"
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
              setError("");
            }}
            className="w-full rounded-lg border border-slate-300 px-4 py-2.5
                       text-sm focus:outline-none focus:ring-2
                       focus:ring-blue-500 focus:border-blue-500"
          />

          {/* Due date */}
          <input
            type="date"
            required
            value={dueDate}
            onChange={(e) => {
              setDueDate(e.target.value);
              setError("");
            }}
            className={`w-full rounded-lg border px-4 py-2.5 text-sm
              focus:outline-none focus:ring-2
              ${
                isPastDate(dueDate)
                  ? "border-red-500 focus:ring-red-500"
                  : "border-slate-300 focus:ring-blue-500"
              }`}
          />

          {/* Past date warning */}
          {isPastDate(dueDate) && (
            <p className="text-sm text-red-600">
              Due date cannot be in the past. Please choose a future date.
            </p>
          )}

          {/* Description */}
          <textarea
            required
            placeholder="Task description"
            value={description}
            onChange={(e) => {
              setDescription(e.target.value);
              setError("");
            }}
            rows={4}
            className="w-full resize-none rounded-lg border border-slate-300
                       px-4 py-2.5 text-sm focus:outline-none
                       focus:ring-2 focus:ring-blue-500
                       focus:border-blue-500"
          />

          {/* Generic error */}
          {error && (
            <p className="text-sm text-red-600">
              {error}
            </p>
          )}

          {/* Actions */}
          <div className="mt-6 flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              className="rounded-lg px-4 py-2 text-sm font-medium
                         text-slate-600 hover:bg-slate-100 transition"
            >
              Cancel
            </button>

            <button
              type="submit"
              disabled={!isFormValid}
              className="rounded-lg bg-blue-600 px-5 py-2
                         text-sm font-medium text-white
                         hover:bg-blue-700 transition
                         disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {task ? "Update" : "Create"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
