import numpy as np
import scipy.stats as si
import math
import random
import pandas as pd
import csv
import xlwt
import matplotlib
import matplotlib.pyplot as plt


# Global variable
Max_sharpe_ratio =0
learning_rate = 0.01
gamma_reward_decay = 0.85
epsilon = 0.7
start = 0
money_earned=0
window = 20
profit_pertransaction=[]
suppose_fluctuation_spot=-0.06
actions = ["LONG3D","LONG5D","LONG10D","SHORT3D","SHORT5D","SHORT10D","WAIT"]
count_0=0
count_1=0
count_2=0
count_3=0
count_4=0
dataset=[1]
cross_validation_set1 = [[None] * 4 for i in range(400)]
cross_validation_set2 = [[None] * 4 for i in range(150)]
profit_list=[]
sharpe_ratiolist=[]
pl_list=[]



# Create a Qlearning_table
Qlearning_table = pd.DataFrame([[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0]
,[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0]
,[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0]],
columns=["LONG3D","LONG5D","LONG10D","SHORT3D","SHORT5D","SHORT10D","WAIT"],
index=["Saa","Sab","Sac","Sad","Sae","Sba","Sbb","Sbc","Sbd","Sbe","Sca","Scb","Scc","Scd","Sce","Sda","Sdb","Sdc","Sdd","Sde","Sea","Seb","Sec","Sed","See"])


# Pricing model (put & call)
def euro_vanilla_call(S, K, T, r, sigma):
    # S: spot price
    # K: strike price
    # T: time to maturity
    # r: interest rate
    # sigma: volatility of underlying asset

    d1 = (np.log(S / K) + (r + 0.5 * sigma ** 2) * T) / (sigma * np.sqrt(T))
    d2 = (np.log(S / K) + (r - 0.5 * sigma ** 2) * T) / (sigma * np.sqrt(T))

    call = (S * si.norm.cdf(d1, 0.0, 1.0) - K * np.exp(-r * T) * si.norm.cdf(d2, 0.0, 1.0))

    return call


def euro_vanilla_put(S, K, T, r, sigma):

    d1 = (np.log(S / K) + (r + 0.5 * sigma ** 2) * T) / (sigma * np.sqrt(T))
    d2 = (np.log(S / K) + (r - 0.5 * sigma ** 2) * T) / (sigma * np.sqrt(T))

    put = (K * np.exp(-r * T) * si.norm.cdf(-d2, 0.0, 1.0) - S * si.norm.cdf(-d1, 0.0, 1.0))

    return put


# Pricing model of Future
def cost_of_carry_model(S, r, q, T, t):

    price_future = S * math.exp((r - q) * (T - t))

    return price_future


# Original data storage area ( close stock future option open )

originaldata = [[None] * 4 for i in range(736)]



with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\大盤收盤價array0.csv', newline='') as csvfile0:
    rows = csv.reader(csvfile0, delimiter=',')
    for row in rows:
        originaldata[count_0][0] = float(row[4])
        count_0 = count_0 + 1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\現貨買賣array1.csv', newline='') as csvfile1:
    rows = csv.reader(csvfile1, delimiter=',')
    for row in rows:
            originaldata[count_1][1]=int(row[0])
            count_1=count_1+1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\期貨與選擇權未平倉array2&3.csv', newline='') as csvfile2:
    rows = csv.reader(csvfile2, delimiter=',')
    for row in rows:
        if row[1] == '外資及陸資'or row[1]=='外資及陸資(不含外資自營商)':
            originaldata[count_2][2] = int(row[26])
            count_2 = count_2 + 1

with open(r'C:\Users\jerry\Desktop\自行爬蟲資料\期貨與選擇權未平倉array2&3.csv', newline='') as csvfile3:
    rows = csv.reader(csvfile3, delimiter=',')
    for row in rows:
        if row[1] == '外資及陸資'or row[1]=='外資及陸資(不含外資自營商)':
            originaldata[count_3][3] = int(row[27])  # In thousands
            count_3 = count_3 + 1

print(count_0)
print(count_1)
print(count_2)
print(count_3)
countset=0


# Cutting original data (Traning & Testing)
for i in range(0,400):
     cross_validation_set1[i] = originaldata[i]

for i in range(0,150):
     cross_validation_set2[i] = originaldata[i+400]


 # Calculate two States
def bollinger():   # bollinger
    global start
    global window
    time = 1
    stdlist = []
    sum = 0

    for i in range(start, start + window):
        sum = sum + originaldata[i][0]
        stdlist.append(originaldata[i][0])
    mean_price = sum / window
    std = np.std(stdlist)

    upper_band125 = mean_price + 1.3 * std
    upper_band075 = mean_price + 0.7 * std
    lowwer_band075 = mean_price - 0.7 * std
    lowwer_band125 = mean_price - 1.3 * std

    bollinger = []
    bollinger.append(upper_band125)
    bollinger.append(upper_band075)
    bollinger.append(mean_price)
    bollinger.append(lowwer_band075)
    bollinger.append(lowwer_band125)
    return bollinger


def comprehensive_indicator():  # comprehensive_indicator
    global start
    global window
    global suppose_fluctuation_spot
    sum_spot = 0
    sum_future = 0
    sum_option = 0
    profit_spot = 0
    profit_future = 0
    profit_option = 0


    for i in range(start, start + window):  # Calculate profit

        sum_spot = sum_spot + originaldata[i][1]

        profit_spot = sum_spot * suppose_fluctuation_spot

    # Future

    prefuture_price = cost_of_carry_model(originaldata[start + window][0], 0.01, 0, 15, 0)
    spot_price = originaldata[start + window][0] * (1 + suppose_fluctuation_spot)  # suppose TAIEX decline 1%
    afterfuture_price = cost_of_carry_model(spot_price, 0.01, 0, 15, 0)

    loss_percent_future = (afterfuture_price - prefuture_price) / prefuture_price

    for i in range(start, start + window):
        sum_future = sum_future + originaldata[i][2]

        profit_future = sum_future * loss_percent_future  # Calculate profit of future

    # Options

    for i in range(start, start + window):
        sum_option = sum_option + originaldata[i][3]

    if sum_option > 0:
        strike_callprice = 100 * (math.floor((originaldata[start + window][0] / 100)))
        preoption_price = euro_vanilla_call(originaldata[start + window][0], strike_callprice, 15, 0.01, 0.03)
        spot_price = originaldata[start + window][0] * (1 + suppose_fluctuation_spot)
        afteroption_price = euro_vanilla_call(spot_price, strike_callprice, 15, 0.01, 0.03)
        loss_percent_option = (afteroption_price - preoption_price) / preoption_price
        profit_option = sum_option * loss_percent_option

    if sum_option <= 0:
        strike_putprice = 100 * (math.ceil((originaldata[start + window][0] / 100)))
        preoption_price = euro_vanilla_put(originaldata[start + window][0], strike_putprice, 15, 0.01, 0.03)
        spot_price = originaldata[start + window][0] * (1 + suppose_fluctuation_spot)
        afteroption_price = euro_vanilla_put(spot_price, strike_putprice, 15, 0.01, 0.03)
        loss_percent_option = (afteroption_price - preoption_price) / preoption_price
        profit_option = sum_option * loss_percent_option

    total_profit = profit_spot + profit_future + profit_option

    profit_list.append(float(total_profit))

    return total_profit


# Judge status

def pass_state():
    global start
    global window
    global suppose_fluctuation_spot

    state="S"
    bb_value = bollinger()
    compre_value=comprehensive_indicator()

    if  originaldata[start+window][0]>=bb_value[0]:
        state= state+'b'

    elif originaldata[start+window][0] < bb_value[0] and originaldata[start+window][0] >= bb_value[1]  :
        state = state + 'e'

    elif originaldata[start+window][0] < bb_value[1] and originaldata[start+window][0] >= bb_value[3]  :
        state = state + 'c'

    elif originaldata[start + window][0] < bb_value[3] and originaldata[start + window][0] >= bb_value[4]:  #
        state = state + 'a'

    elif originaldata[start+window][0] < bb_value[4]:
        state = state + 'd'

    if compre_value>700000000:
        state= state+'e'

    elif compre_value>300000000 and compre_value<= 700000000:
        state = state +'d'

    elif compre_value > -400000000 and compre_value <= 300000000:
        state = state + 'c'

    elif compre_value > -700000000 and compre_value <= -400000000:
        state = state + 'b'

    elif compre_value <= -700000000:
        state = state + 'a'


    return  state


# Choose actions
def choose_action(state):
    if np.random.uniform(0,1) < epsilon:
        # choose best action
        state_action = Qlearning_table.loc[state, :]
        # some actions may have the same value, randomly choose on in these actions
        action = np.random.choice(state_action[state_action == np.max(state_action)].index)
    else:
        # choose random action
        action = np.random.choice(actions)
    return action


def calculate_profit_nextstate(action): # Calculate reward
    global start
    global window
    global money_earned


    if action =="LONG3D":
        reward = (originaldata[start+window+3][0]-originaldata[start+window][0])*50
        money_earned=money_earned+reward
        nextstate= pass_state()
        start=start+3
    if action == "LONG5D":
        reward = (originaldata[start+window + 5][0] - originaldata[start+window][0]) * 50
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 5
    if action == "LONG10D":
        reward = (originaldata[start + window +10][0] - originaldata[start + window][0]) * 50
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 10

    if action == "SHORT3D":
        reward = (originaldata[start+window + 3][0] - originaldata[start+window][0]) * (-50)
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 3
    if action == "SHORT5D":
        reward = (originaldata[start+window +5][0] - originaldata[start+window][0]) * (-50)
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 5
    if action == "SHORT10D":
        reward = (originaldata[start+window +10][0] - originaldata[start+window][0]) * (-50)
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 10

    if action=="WAIT" :
        reward = -30
        money_earned = money_earned + reward
        nextstate = pass_state()
        start = start + 1

    profit_pertransaction.append(reward)
    return reward,nextstate,start


def learn(state, action, reward, nextstate): # Update Q-table

        q_predict = Qlearning_table.loc[state, action]
        if nextstate != 'terminal':
            q_target = reward + gamma_reward_decay * Qlearning_table.loc[nextstate, :].max()  # next state is not terminal
        else:
            q_target = reward  # next state is terminal

        Qlearning_table.loc[state, action] += learning_rate * (q_target - q_predict)

'''
state = pass_state(start,window,suppose_fluctuation_spot)
action = choose_action(state)
reward,nextstate,start= calculate_profit_nextstate(start,window,action)
learn(state, action, reward, nextstate)
print(state)
print(action)
print(nextstate)
print(reward)
print(start)
print(Qlearning_table)
'''


def training_run():
    global dataset
    for k in dataset:
        print(1)
        if k == 1:
            originaldata = cross_validation_set1




        for episode in range(1000):
            global start
            global money_earned
            start = 0
            state = pass_state()

            while True:
                # choose action based on initial_state
                action = choose_action(state)

                # take action and get nextstate and reward
                reward, nextstate, start = calculate_profit_nextstate(action)

                # learn from this transition
                learn(state, action, reward, nextstate)

                # swap observation

                state = nextstate

                if start >= 339:
                    print('第', episode, '回合訓練損益是:', money_earned, '元')
                    SR = np.mean(profit_pertransaction) / np.std(profit_pertransaction)
                    print('第', episode, '回合訓練的 Sharpe ratio是:', SR)
                    money_earned = 0
                    profit_pertransaction.clear()
                    break


def test_run():
    originaldata = cross_validation_set2
    print(2)


    for episode in range(1):   # Test just one round
        global start
        global money_earned
        start = 0
        state = pass_state()

        while True:
            # choose action based on initial_state
            action = choose_action(state)

            # take action and get nextstate and reward
            reward, nextstate, start = calculate_profit_nextstate(action)

            # learn from this transition
            learn(state, action, reward, nextstate)

            # swap observation

            state = nextstate

            if start >= 98:
                print('第', episode, '回合測試損益是:', money_earned, '元')
                pl_list.append(money_earned)
                SR = np.mean(profit_pertransaction) / np.std(profit_pertransaction)
                print('第', episode, '回合測試的 Sharpe ratio是:', SR)
                sharpe_ratiolist.append(SR)
                break



def cross_run():
    global  Qlearning_table

    training_run()
    test_run()
    print(Qlearning_table)
    return Qlearning_table
    # end of game



cross_run() #  Run programs
Qlearning_table.to_csv(r'C:\Users\jerry\Desktop\3456.csv')
profitlist_table = pd.DataFrame([[0]],
columns=["earned"],
index=["1"])

for i in range(len(profit_pertransaction)):
    profitlist_table[i]=profit_pertransaction[i]

profitlist_table.to_csv(r'C:\Users\jerry\Desktop\999.csv')

