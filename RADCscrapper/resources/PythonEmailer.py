import os, sys
import win32com.client as win32

def Emailer(text, subject, recipient, auto=False):   

    outlook = win32.Dispatch('outlook.application')
    mail = outlook.CreateItem(0)
    mail.To = recipient
    mail.Subject = subject
    mail.HtmlBody = text
    if auto:
        mail.send
    else:
        mail.Display(True) # or whatever the correct code is

def create_email(path):
    with open(path, 'r') as myfile:      
        text = myfile.read()
        Emailer(text, "Next Week's Orders", 'email@validemailaddress.com', False)

create_email(sys.argv[1])