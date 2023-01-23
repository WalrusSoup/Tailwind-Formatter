class Ui::ButtonComponent < ViewComponent::Base
  TAGS = Set.new([:a, :button, :label, :submit]).freeze
  LEVELS = {
    danger: "border-transparent shadow-sm text-white bg-red-500 hover:bg-red-600",
  }.freeze

  def initialize
  end
end