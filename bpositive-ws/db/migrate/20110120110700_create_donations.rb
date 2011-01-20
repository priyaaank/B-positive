class CreateDonations < ActiveRecord::Migration
  def self.up
    create_table :donations do |t|
      t.datetime :date_of_donation

      t.timestamps
    end
  end

  def self.down
    drop_table :donations
  end
end
