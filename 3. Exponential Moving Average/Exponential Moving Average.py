import numpy as np
import scipy.stats as si
import math
import random
import pandas as pd
import csv

# global variable
window=20
count_0 = 0
count_1 = 0
count_2 = 0
count_3 = 0
count_4 = 0
profit_pertransaction = []
originaldata = [[None] * 4 for i in range(736)]


# Read data from csv
with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\大盤收盤價array0.csv', newline='') as csvfile0:
    rows = csv.reader(csvfile0, delimiter=',')
    for row in rows:
        originaldata[count_0][0] = float(row[4])
        count_0 = count_0 + 1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\現貨買賣array1.csv', newline='') as csvfile1:
    rows = csv.reader(csvfile1, delimiter=',')
    for row in rows:
        originaldata[count_1][1] = int(row[0])
        count_1 = count_1 + 1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\期貨與選擇權未平倉array2&3.csv', newline='') as csvfile2:
    rows = csv.reader(csvfile2, delimiter=',')
    for row in rows:
        if row[1] == '外資及陸資' or row[1] == '外資及陸資(不含外資自營商)':
            originaldata[count_2][2] = int(row[26])
            count_2 = count_2 + 1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\期貨與選擇權未平倉array2&3.csv', newline='') as csvfile3:
    rows = csv.reader(csvfile3, delimiter=',')
    for row in rows:
        if row[1] == '外資及陸資' or row[1] == '外資及陸資(不含外資自營商)':
            originaldata[count_3][3] = int(row[27])  # 以千元為單位
            count_3 = count_3 + 1


def EMA(start, number):
    price_begin = originaldata[start][0]
    if number >= 2:
        ema_now = 1 / number * originaldata[start + number][0] + (1 - 1 / number) * EMA(start, number - 1)
        return ema_now

    if number == 1:
        return price_begin



for day in range(400, 549, 1):

    ema_prevalue = EMA(day-1,window)
    ema_nowvalue = EMA(day, window)

    if originaldata[day-1+window][0]< ema_prevalue and originaldata[day+window][0] >= ema_nowvalue:
        profit = (originaldata[day + window+2][0] - originaldata[day + window+1][0]) * 50   #做多
        profit_pertransaction.append(profit)
    if originaldata[day+window][0] >= ema_nowvalue < originaldata[day-1+window][0]< ema_prevalue:
        profit = (originaldata[day + 2+window][0] - originaldata[day + 1+window][0]) * -50
        profit_pertransaction.append(profit)

SR = np.mean(profit_pertransaction) / np.std(profit_pertransaction)

print(SR)

profitlist_table = pd.DataFrame([[0]],
columns=["earned"],
index=["1"])

for i in range(len(profit_pertransaction)):
    profitlist_table[i]=profit_pertransaction[i]

print(profitlist_table)

profitlist_table.to_csv(r'C:\Users\jerry\Desktop\888.csv')