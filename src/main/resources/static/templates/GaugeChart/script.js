window.onload = function() {
    // Get the canvas and context
    var canvas = document.getElementById("speedometer");
    var ctx = canvas.getContext("2d");
    
    // Speedometer settings
    var centerX = canvas.width / 2;
    var centerY = canvas.height - 20;
    var radius = 150;
    var minValue = 0;
    var maxValue = 200;
    var currentValue = 50; // Current value to be displayed by the needle

    // Draw the background arc
    drawArc(ctx, centerX, centerY, radius, Math.PI, 2 * Math.PI, "#e6e6e6");

    // Draw the colored segments
    drawSegment(ctx, centerX, centerY, radius, 0, 60, "#3498db"); // Blue (0-60)
    drawSegment(ctx, centerX, centerY, radius, 60, 120, "#2ecc71"); // Green (60-120)
    drawSegment(ctx, centerX, centerY, radius, 120, 160, "#f1c40f"); // Yellow (120-160)
    drawSegment(ctx, centerX, centerY, radius, 160, 200, "#e74c3c"); // Red (160-200)

    // Draw the needle
    drawNeedle(ctx, centerX, centerY, radius, currentValue, minValue, maxValue);

    // Draw the text value
    ctx.font = "20px Arial";
    ctx.fillStyle = "#333";
    ctx.textAlign = "center";
    ctx.fillText(currentValue + " A/V", centerX, centerY + 40);
};

// Function to draw an arc
function drawArc(ctx, centerX, centerY, radius, startAngle, endAngle, color) {
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, startAngle, endAngle);
    ctx.lineWidth = 15;
    ctx.strokeStyle = color;
    ctx.stroke();
}

// Function to draw a segment based on min and max values
function drawSegment(ctx, centerX, centerY, radius, minValue, maxValue, color) {
    var startAngle = (minValue / 200) * Math.PI;
    var endAngle = (maxValue / 200) * Math.PI;
    drawArc(ctx, centerX, centerY, radius, Math.PI + startAngle, Math.PI + endAngle, color);
}

// Function to draw the needle
function drawNeedle(ctx, centerX, centerY, radius, value, minValue, maxValue) {
    var angle = (value / maxValue) * Math.PI;
    var needleLength = radius - 20;

    ctx.beginPath();
    ctx.moveTo(centerX, centerY); // Starting point of needle
    ctx.lineTo(centerX + needleLength * Math.cos(Math.PI + angle), centerY + needleLength * Math.sin(Math.PI + angle));
    ctx.lineWidth = 5;
    ctx.strokeStyle = "#333";
    ctx.stroke();
}
