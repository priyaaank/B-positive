class CreateLocations < ActiveRecord::Migration
  def self.up
    create_table :locations do |t|
      t.string :address_line1
      t.string :address_line2
      t.string :city
      t.string :country
      t.string :zip
      t.string :state
      t.string :latitude
      t.string :longitude

      t.timestamps
    end
  end

  def self.down
    drop_table :locations
  end
end
