using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ObjectMoving
{
    public partial class FormView : Form
    {
        enum Position
        {
            Left, Right, Up, Down, Start
        }

        private int _x;
        private int _y;
        private int[] x_desk = new int[4];
        private int[] y_desk = new int[4];
        private Boolean draw;
        private int speed;
        private Position _objPosition;

        public FormView()
        {
            InitializeComponent();
            Console.Write("testt");
            draw = true;

            _x = 50;
            _y = 50;

            Random random = new Random();

            x_desk[0] = random.Next(50, 500);
            y_desk[0] = random.Next(100, 520);

            x_desk[1] = random.Next(50, 500);
            y_desk[1] = random.Next(100, 520);

            x_desk[2] = random.Next(50, 500);
            y_desk[2] = random.Next(100, 520);

            speed = 5;
            _objPosition = Position.Down;
            //_objPosition = Position.Start;
            Console.Write(_y.ToString());
        }

        private void FormView_Paint(object sender, PaintEventArgs e)
        {
            //e.Graphics.FillRectangle(Brushes.BlueViolet, _x, _y, 100, 100);
            e.Graphics.DrawImage(new Bitmap("waiter.png"), _x, _y, 100, 79);

            for (int i = 0; i < 2; i++)
            {
                e.Graphics.DrawImage(new Bitmap("table.png"), x_desk[i], y_desk[i], 50, 50);
            }
        }

        private void tmrMoving_Tick(object sender, EventArgs e)
        {
            label1.Text = "X: " + _x.ToString();
            label2.Text = "Y: " + _y.ToString();
            
            Console.Write(_y.ToString());
            if (_objPosition == Position.Right)
            {
                _x += speed;
                if (_x > 600)
                {
                    _objPosition = Position.Left;
                }
            }
            else if (_objPosition == Position.Left)
            {
                _x -= speed;
                if (_x < 0)
                {
                    _objPosition = Position.Right;
                }
            }
            else if (_objPosition == Position.Up)
            {
                _y -= speed;
                if (_y < 20)
                {
                    _objPosition = Position.Down;
                }
            }
            else if (_objPosition == Position.Down)
            {
                _y += speed;
                if (_y > 500)
                {
                    _objPosition = Position.Up;
                }
      
            }

            /*if((_x == x_desk[0] && _y == y_desk[0]) || (_x == x_desk[0]+50 && _y == y_desk[0]+50) 
                || (_x == x_desk[1]+50 && _y == y_desk[1]+50) || (_x == x_desk[1]+50 && _y == y_desk[1]+50))
            {
                _objPosition = Position.Start;
            }*/

            Invalidate();
        }

        private void FormView_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Left)
            {
                _objPosition = Position.Left;
            }
            else if (e.KeyCode == Keys.Right)
            {
                _objPosition = Position.Right;
            }
            else if (e.KeyCode == Keys.Up)
            {
                _objPosition = Position.Up;
            }
            else if (e.KeyCode == Keys.Down)
            {
                _objPosition = Position.Down;
            }
        }

        private void FormView_Load(object sender, EventArgs e)
        {

        }


   
    }
}
