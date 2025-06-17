<h2>📄 Page List</h2>

<h3>1. Authentication & Account</h3>
<ul>
  <li><strong>Login Page</strong> - User sign-in</li>
  <li><strong>Register Page</strong> - New account creation</li>
  <li><strong>Forgot Password Page</strong> - Password recovery</li>
  <li><strong>Manage Account Page</strong> - Profile, security, and active sessions</li>
</ul>

<h3>2. Core Navigation</h3>
<ul>
  <li><strong>Homepage</strong> - Server list and friends</li>
  <li><strong>Server Discovery Page</strong> - Explore public communities</li>
</ul>

<h3>3. Server (Community) Pages</h3>
<ul>
  <li><strong>Server Homepage</strong> - Channel list with member sidebar</li>
  <li><strong>Server Settings Page</strong> - Configuration and moderation</li>
  <li><strong>Server Invite Page</strong> - Create/share invitation links</li>
  <li><strong>Server Members Page</strong> - User management</li>
</ul>

<h3>4. Channel Pages</h3>
<ul>
  <li><strong>Text Channel Page</strong> - Message feed with input</li>
  <li><strong>Voice Channel Page</strong> - Voice chat interface</li>
  <li><strong>Channel Settings Page</strong> - Permissions and pinned messages</li>
</ul>

<h3>5. Direct Messaging</h3>
<ul>
  <li><strong>Conversation Page</strong> - 1:1 or group DMs</li>
  <li><strong>Friends List Page</strong> - Manage connections</li>
</ul>

<h3>6. Utility Pages</h3>
<ul>
  <li><strong>Notifications Page</strong> - Mentions and alerts</li>
  <li><strong>Search Page</strong> - Global message search</li>
  <li><strong>Settings Page</strong> - App customization</li>
</ul>

<h3>7. Admin Pages</h3>
<ul>
  <li><strong>Admin Dashboard</strong> - Analytics and reports</li>
  <li><strong>Role Management Page</strong> - Permission configuration</li>
</ul>

<h3>🌟 MVP Priority</h3>
<ol>
  <li>Login/Register</li>
  <li>Server Homepage</li>
  <li>Text Channel Page</li>
  <li>Conversation Page</li>
</ol>

<h2>User</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Message</td>
        <td>messages</td>
        <td>A user can author many messages</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Community</td>
        <td>communities</td>
        <td>A user can own many communities (servers)</td>
    </tr>
    <tr>
        <td>Many-to-Many</td>
        <td>Community</td>
        <td>joinedCommunities</td>
        <td>Users can join multiple communities</td>
    </tr>
    <tr>
        <td>Many-to-Many</td>
        <td>Role</td>
        <td>roles</td>
        <td>Users can have multiple roles</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Session</td>
        <td>sessions</td>
        <td>A user can have multiple active sessions</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Invite</td>
        <td>createdInvites</td>
        <td>A user can create many invites</td>
    </tr>
</table>
<h2>Community</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>User</td>
        <td>owner</td>
        <td>Each community has one owner</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Channel</td>
        <td>channels</td>
        <td>A community contains many channels</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Role</td>
        <td>roles</td>
        <td>A community can define multiple roles</td>
    </tr>
    <tr>
        <td>Many-to-Many</td>
        <td>User</td>
        <td>members</td>
        <td>Communities have many members</td>
    </tr>
</table>
<h2>Channel</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>Community</td>
        <td>community</td>
        <td>Each channel belongs to one community</td>
    </tr>
    <tr>
        <td>One-to-Many</td>
        <td>Message</td>
        <td>messages</td>
        <td>A channel contains many messages</td>
    </tr>
    <tr>
        <td>-</td>
        <td>-</td>
        <td>type</td>
        <td>ChannelType enum (TEXT/VOICE/VIDEO)</td>
    </tr>
</table>
<h2>Message</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>User</td>
        <td>author</td>
        <td>Each message has one author</td>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>Channel</td>
        <td>channel</td>
        <td>Each message belongs to one channel</td>
    </tr>
</table>
<h2>Role</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>Community</td>
        <td>community</td>
        <td>Roles are scoped to a community</td>
    </tr>
    <tr>
        <td>Many-to-Many</td>
        <td>User</td>
        <td>users</td>
        <td>Roles can be assigned to many users</td>
    </tr>
</table>
<h2>Session</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>User</td>
        <td>user</td>
        <td>Each session is tied to one user</td>
    </tr>
</table>
<h2>Invite</h2>
<table border="1">
    <tr>
        <th>Relationship Type</th>
        <th>Target Entity</th>
        <th>Field Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>Community</td>
        <td>community</td>
        <td>Each invite is for one community</td>
    </tr>
    <tr>
        <td>Many-to-One</td>
        <td>User</td>
        <td>createdBy</td>
        <td>Each invite is created by one user</td>
    </tr>
</table>
