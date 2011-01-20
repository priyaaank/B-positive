class CreateDonors < ActiveRecord::Migration
  def self.up
    create_table :donors do |t|
      t.string :name
      t.string :bloodgroup
      t.integer :age
      t.date :date_of_birth

      t.timestamps
    end
  end

  def self.down
    drop_table :donors
  end
end
