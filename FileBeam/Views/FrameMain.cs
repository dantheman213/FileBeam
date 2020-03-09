﻿using System;
using System.Windows.Forms;

namespace FileBeam
{
    public partial class FrameMain : Form
    {
        private string[] fruits = new string[] { "Watermelon", "Lemon", "Strawberry", "Mango", "Plum", "Avocado", "Cherry", "Blueberry", "Papaya", "Banana", "Pineapple", "Orange", "Peach", "Pear", "Grape" };
        private string[] nouns = new string[] { "Bear", "Fox", "Cat", "Dog", "Giraffe", "Rockstar", "Airplane", "Car", "Boat", "Spaceship", "Cloud", "Ocean", "Book", "Star", "Candy" };
        public static string DeviceName;
        public static bool Discoverable;

        public FrameMain()
        {
            InitializeComponent();
        }

        private void FrameMain_Load(object sender, System.EventArgs e)
        {
            var rnd = new Random();
            int idx1 = rnd.Next(fruits.Length);
            int idx2 = rnd.Next(nouns.Length);
            DeviceName = String.Format("{0} {1}", fruits[idx1], nouns[idx2]);
        }
    }
}