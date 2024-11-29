create-volume:
	@printf "Creating volume..."
	@docker volume create volume-strava-db
	@echo -e "\n volume created."

drop-volume:
	@printf "Dropping volume..."
	@docker stop strava-db && docker rm strava-db && docker volume rm volume-strava-db
	@echo -e "\n volume dropped."
