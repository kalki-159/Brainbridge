const API = "/api";
let currentUser = JSON.parse(localStorage.getItem("brainbridgeUser") || "null");

document.addEventListener("DOMContentLoaded", () => {
    if (currentUser) {
        showDashboard();
    } else {
        showSection("home");
    }

    document.getElementById("registerForm").addEventListener("submit", register);
    document.getElementById("loginForm").addEventListener("submit", login);
});

function showSection(id) {
    document.querySelectorAll(".section").forEach(section => section.classList.add("hidden"));
    document.getElementById(id).classList.remove("hidden");
    window.scrollTo({top: 0, behavior: "smooth"});
}

async function register(event) {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.target).entries());

    try {
        const response = await fetch(`${API}/users/register`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (!response.ok) throw new Error(result.error || "Registration failed.");

        currentUser = result;
        localStorage.setItem("brainbridgeUser", JSON.stringify(result));
        toast("Account created successfully!");
        showDashboard();
    } catch (error) {
        toast(error.message);
    }
}

async function login(event) {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.target).entries());

    try {
        const response = await fetch(`${API}/users/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (!response.ok) throw new Error(result.error || "Login failed.");

        currentUser = result;
        localStorage.setItem("brainbridgeUser", JSON.stringify(result));
        toast("Welcome back!");
        showDashboard();
    } catch (error) {
        toast(error.message);
    }
}

function showDashboard() {
    document.getElementById("sessionArea").innerHTML =
        `<span class="muted">Hi, ${escapeHtml(currentUser.name)}</span>`;

    showSection("dashboard");
    renderProfile();
    loadMatches();
    loadConnections();
}

function renderProfile() {
    const fields = [
        ["Name", currentUser.name],
        ["Email", currentUser.email],
        ["Skills", currentUser.skills],
        ["Learning Goals", currentUser.learningGoals],
        ["Interests", currentUser.interests],
        ["Collaboration", currentUser.collaborationPreferences]
    ];

    document.getElementById("profileContent").innerHTML = fields.map(([label, value]) => `
        <div class="profile-field">
            <small>${label}</small>
            <strong>${escapeHtml(value || "Not added")}</strong>
        </div>
    `).join("");
}

async function loadMatches() {
    const container = document.getElementById("matches");
    container.innerHTML = `<p class="muted">Finding your matches...</p>`;

    try {
        const response = await fetch(`${API}/matches/${currentUser.id}`);
        const matches = await response.json();

        if (!matches.length) {
            container.innerHTML = `<p class="muted">No strong matches found yet. Add more skills and goals to improve matching.</p>`;
            return;
        }

        container.innerHTML = matches.map(match => `
            <div class="match-card">
                <div class="match-top">
                    <div>
                        <strong>${escapeHtml(match.name)}</strong>
                        <p>${escapeHtml(match.bio || "BrainBridge member")}</p>
                    </div>
                    <span class="score">${match.score}% match</span>
                </div>

                <div class="tags">
                    ${tags(match.matchedNeeds, "You can learn")}
                    ${tags(match.reciprocalMatches, "They can learn")}
                    ${tags(match.sharedInterests, "Shared")}
                </div>

                <p><strong>Skills:</strong> ${escapeHtml(match.skills || "Not specified")}</p>
                <button class="btn primary small" onclick="sendConnection(${match.userId})">
                    Connect
                </button>
            </div>
        `).join("");
    } catch (error) {
        container.innerHTML = `<p class="muted">Could not load matches.</p>`;
    }
}

function tags(items, prefix) {
    if (!items || !items.length) return "";
    return items.map(item => `<span class="tag">${prefix}: ${escapeHtml(item)}</span>`).join("");
}

async function sendConnection(receiverId) {
    try {
        const response = await fetch(
            `${API}/connections?senderId=${currentUser.id}&receiverId=${receiverId}`,
            {method: "POST"}
        );

        const result = await response.json();

        if (!response.ok) throw new Error(result.error || "Could not send request.");

        toast("Connection request sent!");
        loadConnections();
    } catch (error) {
        toast(error.message);
    }
}

async function loadConnections() {
    const container = document.getElementById("connections");

    try {
        const response = await fetch(`${API}/connections/${currentUser.id}`);
        const connections = await response.json();

        if (!connections.length) {
            container.innerHTML = `<p class="muted">No connection requests yet.</p>`;
            return;
        }

        container.innerHTML = connections.map(connection => {
            const isReceiver = connection.receiver.id === currentUser.id;
            const other = isReceiver ? connection.sender : connection.receiver;

            return `
                <div class="connection">
                    <div>
                        <strong>${escapeHtml(other.name)}</strong>
                        <div class="muted">${isReceiver ? "Incoming request" : "Request sent"}</div>
                    </div>
                    <div>
                        <span class="status ${connection.status}">${connection.status}</span>
                        ${
                            isReceiver && connection.status === "PENDING"
                            ? `
                                <button class="btn primary small" onclick="updateConnection(${connection.id}, 'accept')">Accept</button>
                                <button class="btn secondary small" onclick="updateConnection(${connection.id}, 'reject')">Reject</button>
                              `
                            : ""
                        }
                    </div>
                </div>
            `;
        }).join("");
    } catch (error) {
        container.innerHTML = `<p class="muted">Could not load connections.</p>`;
    }
}

async function updateConnection(id, action) {
    try {
        const response = await fetch(
            `${API}/connections/${id}/${action}?receiverId=${currentUser.id}`,
            {method: "PUT"}
        );

        const result = await response.json();

        if (!response.ok) throw new Error(result.error || "Update failed.");

        toast(`Request ${action}ed.`);
        loadConnections();
    } catch (error) {
        toast(error.message);
    }
}

function logout() {
    localStorage.removeItem("brainbridgeUser");
    currentUser = null;
    document.getElementById("sessionArea").innerHTML = `
        <button class="btn secondary" onclick="showSection('login')">Login</button>
        <button class="btn primary" onclick="showSection('register')">Join BrainBridge</button>
    `;
    showSection("home");
}

function toast(message) {
    const element = document.getElementById("toast");
    element.textContent = message;
    element.style.display = "block";
    clearTimeout(window.toastTimer);
    window.toastTimer = setTimeout(() => element.style.display = "none", 3000);
}

function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
