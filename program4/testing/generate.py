#!/usr/bin/python

import random

colors = [ 'aliceblue', 'antiquewhite', 'aqua', 'aquamarine', 'azure',
	'beige', 'bisque', 'black', 'blanchedalmond', 'blue', 'blueviolet',
	'brown', 'burlywood', 'cadetblue', 'chartreuse', 'chocolate',
	'coral', 'cornflowerblue', 'cornsilk', 'crimson', 'cyan',
	'darkblue', 'darkcyan', 'darkgoldenrod', 'darkgray', 'darkgreen',
	'darkgrey', 'darkkhaki', 'darkmagenta', 'darkolivegreen', 'darkorange',
	'darkorchid', 'darkred', 'darksalmon', 'darkseagreen', 'darkslateblue',
	'darkslategray', 'darkslategrey', 'darkturquoise', 'darkviolet',
	'deeppink', 'deepskyblue', 'dimgray', 'dimgrey', 'dodgerblue',
	'firebrick', 'floralwhite', 'forestgreen', 'fuchsia', 'gainsboro',
	'ghostwhite', 'gold', 'goldenrod', 'gray', 'grey', 'green',
	'greenyellow', 'honeydew', 'hotpink', 'indianred', 'indigo',
	'ivory', 'khaki', 'lavender', 'lavenderblush', 'lawngreen',
	'lemonchiffon', 'lightblue', 'lightcoral', 'lightcyan',
	'lightgoldenrodyellow', 'lightgray', 'lightgreen', 'lightgrey',
	'lightpink', 'lightsalmon', 'lightseagreen', 'lightskyblue',
	'lightslategray', 'lightslategrey', 'lightsteelblue', 'lightyellow',
	'lime', 'limegreen', 'linen', 'magenta', 'maroon', 'mediumaquamarine',
	'mediumblue', 'mediumorchid', 'mediumpurple', 'mediumseagreen',
	'mediumslateblue', 'mediumspringgreen', 'mediumturquoise',
	'mediumvioletred', 'midnightblue', 'mintcream', 'mistyrose',
	'moccasin', 'navajowhite', 'navy', 'oldlace', 'olive', 'olivedrab',
	'orange', 'orangered', 'orchid', 'palegoldenrod', 'palegreen',
	'paleturquoise', 'palevioletred', 'papayawhip', 'peachpuff',
	'peru', 'pink', 'plum', 'powderblue', 'purple', 'red', 'rosybrown',
	'royalblue', 'saddlebrown', 'salmon', 'sandybrown', 'seagreen',
	'seashell', 'sienna', 'silver', 'skyblue', 'slateblue', 'slategray',
	'slategrey', 'snow', 'springgreen', 'steelblue', 'tan', 'teal',
	'thistle', 'tomato', 'turquoise', 'violet', 'wheat', 'white',
	'whitesmoke', 'yellow', 'yellowgreen']

gridcol = 100

if __name__ == '__main__':
	grid = {}
	for r in range(50*gridcol):
#		insert two
		x = random.randrange(0, gridcol)
		y = random.randrange(0, gridcol)
		s = random.choice(colors)
		grid[(x,y)] = s
		print 'insert', x, y, s
		
		x = random.randrange(0, gridcol)
		y = random.randrange(0, gridcol)
		s = random.choice(colors)
		grid[(x,y)] = s
		print 'insert', x, y, s

#		look up one present
		xy = random.choice(grid.keys())
		print 'query', xy[0], xy[1]
		print '#RESPONSE query:', grid[xy]

#		look up one at random
		x = random.randrange(0, gridcol)
		y = random.randrange(0, gridcol)
		print 'query', x, y
		if (x,y) in grid:
			print '#RESPONSE query:', grid[(x,y)]
		else:
			print '#RESPONSE query: not found'

#		delete one present
		xy = random.choice(grid.keys())
		print 'delete', xy[0], xy[1]
		print '#RESPONSE delete:', grid[xy]
		del grid[xy]

#		delete at random
		x = random.randrange(0, gridcol)
		y = random.randrange(0, gridcol)
		print 'delete', x, y
		if (x,y) in grid:
			print '#RESPONSE delete:', grid[(x,y)]
			del grid[(x,y)]
		else:
			print '#RESPONSE delete: not found'

