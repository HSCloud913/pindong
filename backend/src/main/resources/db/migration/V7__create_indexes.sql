CREATE INDEX idx_pins_location ON pins USING GIST (location);
CREATE INDEX idx_pins_complex ON pins (neighborhood_id, status);
CREATE INDEX idx_pins_expires_at ON pins (expires_at);
CREATE INDEX idx_neighborhoods_boundary ON neighborhoods USING GIST (boundary);
CREATE INDEX idx_comments_pin_id ON comments (pin_id);
CREATE INDEX idx_comments_user_id ON comments (user_id);
CREATE INDEX idx_reactions_pin_id ON reactions (pin_id);
CREATE INDEX idx_reactions_user_id ON reactions (user_id);