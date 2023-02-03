class Ui::ButtonComponent < ViewComponent::Base
  TAGS = Set.new([:a, :button, :label, :submit]).freeze
  LEVELS = {
    danger: "hover:bg-red-600 bg-red-500 text-white shadow-sm border-transparent",
  }.freeze

  def initialize
  end
end