the antialiasing used super sampling technique in order to make to scenes look smoother
for each pixel, instead of one ray, (antialiasing factor)^2 rays are being shot,
for pixel (x,y)->we shoot (x + i/(antialiasing)^2), where i is in {1, ... , antialiasing}
we sum the values returned by each ray, and the avarege of those pixels is the value of the pixel (x, y)