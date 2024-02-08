db.createUser({
  user: "analysis-app",
  pwd: "password",
  roles: [{
    role: "readWrite", db: "statistics"
  }]
});
