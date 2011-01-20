require 'spec/spec_helper'

describe Donor do

  it "should have a name attribute" do
    Donor.should respond_to(:name)
  end

end
